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

import { getEntities, reset } from './item.reducer';

export const Item = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const itemList = useAppSelector(state => state.item.entities);
  const loading = useAppSelector(state => state.item.loading);
  const links = useAppSelector(state => state.item.links);
  const updateSuccess = useAppSelector(state => state.item.updateSuccess);

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
      <h2 id="item-heading" data-cy="ItemHeading">
        <Translate contentKey="restaurantApp.item.home.title">Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.item.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/item/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.item.home.createLabel">Create new Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={itemList ? itemList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {itemList && itemList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.item.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('itemName')}>
                    <Translate contentKey="restaurantApp.item.itemName">Item Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('itemName')} />
                  </th>
                  <th className="hand" onClick={sort('imageUrl')}>
                    <Translate contentKey="restaurantApp.item.imageUrl">Image Url</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('imageUrl')} />
                  </th>
                  <th className="hand" onClick={sort('properties')}>
                    <Translate contentKey="restaurantApp.item.properties">Properties</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('properties')} />
                  </th>
                  <th className="hand" onClick={sort('foodType')}>
                    <Translate contentKey="restaurantApp.item.foodType">Food Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('foodType')} />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.item.nutrition">Nutrition</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.item.price">Price</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.item.quantity">Quantity</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.item.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {itemList.map((item, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/item/${item.id}`} color="link" size="sm">
                        {item.id}
                      </Button>
                    </td>
                    <td>{item.itemName}</td>
                    <td>{item.imageUrl}</td>
                    <td>{item.properties}</td>
                    <td>
                      <Translate contentKey={`restaurantApp.FoodType.${item.foodType}`} />
                    </td>
                    <td>{item.nutrition ? <Link to={`/nutrition/${item.nutrition.id}`}>{item.nutrition.id}</Link> : ''}</td>
                    <td>{item.price ? <Link to={`/price/${item.price.id}`}>{item.price.id}</Link> : ''}</td>
                    <td>{item.quantity ? <Link to={`/quantity/${item.quantity.id}`}>{item.quantity.id}</Link> : ''}</td>
                    <td>{item.category ? <Link to={`/food-category/${item.category.id}`}>{item.category.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/item/${item.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/item/${item.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (location.href = `/item/${item.id}/delete`)}
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
                <Translate contentKey="restaurantApp.item.home.notFound">No Items found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Item;
