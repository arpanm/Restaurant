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

import { getEntities, reset } from './location.reducer';

export const Location = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const locationList = useAppSelector(state => state.location.entities);
  const loading = useAppSelector(state => state.location.loading);
  const links = useAppSelector(state => state.location.links);
  const updateSuccess = useAppSelector(state => state.location.updateSuccess);

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
      <h2 id="location-heading" data-cy="LocationHeading">
        <Translate contentKey="restaurantApp.location.home.title">Locations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.location.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/location/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.location.home.createLabel">Create new Location</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={locationList ? locationList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {locationList && locationList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.location.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('saveAs')}>
                    <Translate contentKey="restaurantApp.location.saveAs">Save As</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('saveAs')} />
                  </th>
                  <th className="hand" onClick={sort('gst')}>
                    <Translate contentKey="restaurantApp.location.gst">Gst</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('gst')} />
                  </th>
                  <th className="hand" onClick={sort('pan')}>
                    <Translate contentKey="restaurantApp.location.pan">Pan</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pan')} />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    <Translate contentKey="restaurantApp.location.email">Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                  </th>
                  <th className="hand" onClick={sort('phone')}>
                    <Translate contentKey="restaurantApp.location.phone">Phone</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('phone')} />
                  </th>
                  <th className="hand" onClick={sort('streetAddress')}>
                    <Translate contentKey="restaurantApp.location.streetAddress">Street Address</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('streetAddress')} />
                  </th>
                  <th className="hand" onClick={sort('postalCode')}>
                    <Translate contentKey="restaurantApp.location.postalCode">Postal Code</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('postalCode')} />
                  </th>
                  <th className="hand" onClick={sort('area')}>
                    <Translate contentKey="restaurantApp.location.area">Area</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('area')} />
                  </th>
                  <th className="hand" onClick={sort('city')}>
                    <Translate contentKey="restaurantApp.location.city">City</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('city')} />
                  </th>
                  <th className="hand" onClick={sort('stateProvince')}>
                    <Translate contentKey="restaurantApp.location.stateProvince">State Province</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('stateProvince')} />
                  </th>
                  <th className="hand" onClick={sort('country')}>
                    <Translate contentKey="restaurantApp.location.country">Country</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('country')} />
                  </th>
                  <th className="hand" onClick={sort('latitude')}>
                    <Translate contentKey="restaurantApp.location.latitude">Latitude</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('latitude')} />
                  </th>
                  <th className="hand" onClick={sort('longitude')}>
                    <Translate contentKey="restaurantApp.location.longitude">Longitude</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('longitude')} />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.location.usr">Usr</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {locationList.map((location, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/location/${location.id}`} color="link" size="sm">
                        {location.id}
                      </Button>
                    </td>
                    <td>{location.saveAs}</td>
                    <td>{location.gst}</td>
                    <td>{location.pan}</td>
                    <td>{location.email}</td>
                    <td>{location.phone}</td>
                    <td>{location.streetAddress}</td>
                    <td>{location.postalCode}</td>
                    <td>{location.area}</td>
                    <td>{location.city}</td>
                    <td>{location.stateProvince}</td>
                    <td>{location.country}</td>
                    <td>{location.latitude}</td>
                    <td>{location.longitude}</td>
                    <td>{location.usr ? <Link to={`/application-user/${location.usr.id}`}>{location.usr.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/location/${location.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/location/${location.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (location.href = `/location/${location.id}/delete`)}
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
                <Translate contentKey="restaurantApp.location.home.notFound">No Locations found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Location;
