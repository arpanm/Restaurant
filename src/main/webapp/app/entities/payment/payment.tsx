import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './payment.reducer';

export const Payment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const paymentList = useAppSelector(state => state.payment.entities);
  const loading = useAppSelector(state => state.payment.loading);
  const links = useAppSelector(state => state.payment.links);
  const updateSuccess = useAppSelector(state => state.payment.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="payment-heading" data-cy="PaymentHeading">
        <Translate contentKey="restaurantApp.payment.home.title">Payments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.payment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/payment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.payment.home.createLabel">Create new Payment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={paymentList ? paymentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {paymentList && paymentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.payment.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="restaurantApp.payment.status">Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                  </th>
                  <th className="hand" onClick={sort('vendor')}>
                    <Translate contentKey="restaurantApp.payment.vendor">Vendor</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('vendor')} />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="restaurantApp.payment.type">Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="restaurantApp.payment.amount">Amount</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                  </th>
                  <th className="hand" onClick={sort('initDate')}>
                    <Translate contentKey="restaurantApp.payment.initDate">Init Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('initDate')} />
                  </th>
                  <th className="hand" onClick={sort('updatedDate')}>
                    <Translate contentKey="restaurantApp.payment.updatedDate">Updated Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedDate')} />
                  </th>
                  <th className="hand" onClick={sort('totalRefundAmount')}>
                    <Translate contentKey="restaurantApp.payment.totalRefundAmount">Total Refund Amount</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalRefundAmount')} />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.payment.order">Order</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {paymentList.map((payment, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/payment/${payment.id}`} color="link" size="sm">
                        {payment.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`restaurantApp.PaymentStatus.${payment.status}`} />
                    </td>
                    <td>
                      <Translate contentKey={`restaurantApp.PaymentVendor.${payment.vendor}`} />
                    </td>
                    <td>
                      <Translate contentKey={`restaurantApp.PaymentType.${payment.type}`} />
                    </td>
                    <td>{payment.amount}</td>
                    <td>{payment.initDate ? <TextFormat type="date" value={payment.initDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{payment.updatedDate ? <TextFormat type="date" value={payment.updatedDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{payment.totalRefundAmount}</td>
                    <td>{payment.order ? <Link to={`/order/${payment.order.id}`}>{payment.order.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/payment/${payment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/payment/${payment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (location.href = `/payment/${payment.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="restaurantApp.payment.home.notFound">No Payments found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Payment;
