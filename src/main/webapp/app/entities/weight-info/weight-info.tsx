import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './weight-info.reducer';

export const WeightInfo = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const weightInfoList = useAppSelector(state => state.weightInfo.entities);
  const loading = useAppSelector(state => state.weightInfo.loading);
  const links = useAppSelector(state => state.weightInfo.links);
  const updateSuccess = useAppSelector(state => state.weightInfo.updateSuccess);

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
      <h2 id="weight-info-heading" data-cy="WeightInfoHeading">
        <Translate contentKey="restaurantApp.weightInfo.home.title">Weight Infos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.weightInfo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/weight-info/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.weightInfo.home.createLabel">Create new Weight Info</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={weightInfoList ? weightInfoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {weightInfoList && weightInfoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.weightInfo.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('currentWeight')}>
                    <Translate contentKey="restaurantApp.weightInfo.currentWeight">Current Weight</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('currentWeight')} />
                  </th>
                  <th className="hand" onClick={sort('expectedWeight')}>
                    <Translate contentKey="restaurantApp.weightInfo.expectedWeight">Expected Weight</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('expectedWeight')} />
                  </th>
                  <th className="hand" onClick={sort('heightInInch')}>
                    <Translate contentKey="restaurantApp.weightInfo.heightInInch">Height In Inch</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('heightInInch')} />
                  </th>
                  <th className="hand" onClick={sort('numberOfDays')}>
                    <Translate contentKey="restaurantApp.weightInfo.numberOfDays">Number Of Days</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('numberOfDays')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {weightInfoList.map((weightInfo, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/weight-info/${weightInfo.id}`} color="link" size="sm">
                        {weightInfo.id}
                      </Button>
                    </td>
                    <td>{weightInfo.currentWeight}</td>
                    <td>{weightInfo.expectedWeight}</td>
                    <td>{weightInfo.heightInInch}</td>
                    <td>{weightInfo.numberOfDays}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/weight-info/${weightInfo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/weight-info/${weightInfo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (location.href = `/weight-info/${weightInfo.id}/delete`)}
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
                <Translate contentKey="restaurantApp.weightInfo.home.notFound">No Weight Infos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default WeightInfo;
