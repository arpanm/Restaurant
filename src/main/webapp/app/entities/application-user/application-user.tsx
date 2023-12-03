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

import { getEntities, reset } from './application-user.reducer';

export const ApplicationUser = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const applicationUserList = useAppSelector(state => state.applicationUser.entities);
  const loading = useAppSelector(state => state.applicationUser.loading);
  const links = useAppSelector(state => state.applicationUser.links);
  const updateSuccess = useAppSelector(state => state.applicationUser.updateSuccess);

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
      <h2 id="application-user-heading" data-cy="ApplicationUserHeading">
        <Translate contentKey="restaurantApp.applicationUser.home.title">Application Users</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.applicationUser.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/application-user/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.applicationUser.home.createLabel">Create new Application User</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={applicationUserList ? applicationUserList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {applicationUserList && applicationUserList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.applicationUser.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="restaurantApp.applicationUser.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('password')}>
                    <Translate contentKey="restaurantApp.applicationUser.password">Password</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('password')} />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    <Translate contentKey="restaurantApp.applicationUser.email">Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                  </th>
                  <th className="hand" onClick={sort('phone')}>
                    <Translate contentKey="restaurantApp.applicationUser.phone">Phone</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('phone')} />
                  </th>
                  <th className="hand" onClick={sort('role')}>
                    <Translate contentKey="restaurantApp.applicationUser.role">Role</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('role')} />
                  </th>
                  <th className="hand" onClick={sort('isPhoneValidated')}>
                    <Translate contentKey="restaurantApp.applicationUser.isPhoneValidated">Is Phone Validated</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isPhoneValidated')} />
                  </th>
                  <th className="hand" onClick={sort('isEmailValidated')}>
                    <Translate contentKey="restaurantApp.applicationUser.isEmailValidated">Is Email Validated</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isEmailValidated')} />
                  </th>
                  <th className="hand" onClick={sort('phoneValidatedOn')}>
                    <Translate contentKey="restaurantApp.applicationUser.phoneValidatedOn">Phone Validated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('phoneValidatedOn')} />
                  </th>
                  <th className="hand" onClick={sort('emailValidatedOn')}>
                    <Translate contentKey="restaurantApp.applicationUser.emailValidatedOn">Email Validated On</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('emailValidatedOn')} />
                  </th>
                  <th>
                    <Translate contentKey="restaurantApp.applicationUser.restaurant">Restaurant</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {applicationUserList.map((applicationUser, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/application-user/${applicationUser.id}`} color="link" size="sm">
                        {applicationUser.id}
                      </Button>
                    </td>
                    <td>{applicationUser.name}</td>
                    <td>{applicationUser.password}</td>
                    <td>{applicationUser.email}</td>
                    <td>{applicationUser.phone}</td>
                    <td>
                      <Translate contentKey={`restaurantApp.UserRole.${applicationUser.role}`} />
                    </td>
                    <td>{applicationUser.isPhoneValidated ? 'true' : 'false'}</td>
                    <td>{applicationUser.isEmailValidated ? 'true' : 'false'}</td>
                    <td>
                      {applicationUser.phoneValidatedOn ? (
                        <TextFormat type="date" value={applicationUser.phoneValidatedOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {applicationUser.emailValidatedOn ? (
                        <TextFormat type="date" value={applicationUser.emailValidatedOn} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {applicationUser.restaurant ? (
                        <Link to={`/restaurant/${applicationUser.restaurant.id}`}>{applicationUser.restaurant.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/application-user/${applicationUser.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/application-user/${applicationUser.id}/edit`}
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
                          onClick={() => (location.href = `/application-user/${applicationUser.id}/delete`)}
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
                <Translate contentKey="restaurantApp.applicationUser.home.notFound">No Application Users found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default ApplicationUser;
