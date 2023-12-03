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

import { getEntities, reset } from './refund.reducer';

export const Refund = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const refundList = useAppSelector(state => state.refund.entities);
  const loading = useAppSelector(state => state.refund.loading);
  const links = useAppSelector(state => state.refund.links);
  const updateSuccess = useAppSelector(state => state.refund.updateSuccess);

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
      <h2 id="refund-heading" data-cy="RefundHeading">
        <Translate contentKey="restaurantApp.refund.home.title">Refunds</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.refund.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/refund/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.refund.home.createLabel">Create new Refund</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={refundList ? refundList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {refundList && refundList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.refund.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="restaurantApp.refund.status">Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                  </th>
                  <th className="hand" onClick={sort('vendor')}>
                    <Translate contentKey="restaurantApp.refund.vendor">Vendor</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('vendor')} />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="restaurantApp.refund.amount">Amount</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                  </th>
                  <th className="hand" onClick={sort('initDate')}>
                    <Translate contentKey="restaurantApp.refund.initDate">Init Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('initDate')} />
                  </th>
                  <th className="hand" onClick={sort('updatedDate')}>
                    <Translate contentKey="restaurantApp.refund.updatedDate">Updated Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedDate')} />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.refund.payment">Payment</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {refundList.map((refund, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/refund/${refund.id}`} color="link" size="sm">
                        {refund.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`restaurantApp.RefundStatus.${refund.status}`} />
                    </td>
                    <td>
                      <Translate contentKey={`restaurantApp.PaymentVendor.${refund.vendor}`} />
                    </td>
                    <td>{refund.amount}</td>
                    <td>{refund.initDate ? <TextFormat type="date" value={refund.initDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{refund.updatedDate ? <TextFormat type="date" value={refund.updatedDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{refund.payment ? <Link to={`/payment/${refund.payment.id}`}>{refund.payment.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/refund/${refund.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/refund/${refund.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (location.href = `/refund/${refund.id}/delete`)}
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
                <Translate contentKey="restaurantApp.refund.home.notFound">No Refunds found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Refund;
