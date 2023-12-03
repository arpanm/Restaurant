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

import { getEntities, reset } from './meal-plan-item.reducer';

export const MealPlanItem = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const mealPlanItemList = useAppSelector(state => state.mealPlanItem.entities);
  const loading = useAppSelector(state => state.mealPlanItem.loading);
  const links = useAppSelector(state => state.mealPlanItem.links);
  const updateSuccess = useAppSelector(state => state.mealPlanItem.updateSuccess);

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
      <h2 id="meal-plan-item-heading" data-cy="MealPlanItemHeading">
        <Translate contentKey="restaurantApp.mealPlanItem.home.title">Meal Plan Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.mealPlanItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/meal-plan-item/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.mealPlanItem.home.createLabel">Create new Meal Plan Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={mealPlanItemList ? mealPlanItemList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {mealPlanItemList && mealPlanItemList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.mealPlanItem.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('planItemName')}>
                    <Translate contentKey="restaurantApp.mealPlanItem.planItemName">Plan Item Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('planItemName')} />
                  </th>
                  <th className="hand" onClick={sort('hourValue')}>
                    <Translate contentKey="restaurantApp.mealPlanItem.hourValue">Hour Value</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hourValue')} />
                  </th>
                  <th className="hand" onClick={sort('pincode')}>
                    <Translate contentKey="restaurantApp.mealPlanItem.pincode">Pincode</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pincode')} />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.mealPlanItem.deliveryLocation">Delivery Location</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.mealPlanItem.plan">Plan</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {mealPlanItemList.map((mealPlanItem, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/meal-plan-item/${mealPlanItem.id}`} color="link" size="sm">
                        {mealPlanItem.id}
                      </Button>
                    </td>
                    <td>{mealPlanItem.planItemName}</td>
                    <td>{mealPlanItem.hourValue}</td>
                    <td>{mealPlanItem.pincode}</td>
                    <td>
                      {mealPlanItem.deliveryLocation ? (
                        <Link to={`/location/${mealPlanItem.deliveryLocation.id}`}>{mealPlanItem.deliveryLocation.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{mealPlanItem.plan ? <Link to={`/meal-plan/${mealPlanItem.plan.id}`}>{mealPlanItem.plan.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/meal-plan-item/${mealPlanItem.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/meal-plan-item/${mealPlanItem.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (location.href = `/meal-plan-item/${mealPlanItem.id}/delete`)}
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
                <Translate contentKey="restaurantApp.mealPlanItem.home.notFound">No Meal Plan Items found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default MealPlanItem;
