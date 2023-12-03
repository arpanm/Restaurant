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

import { getEntities, reset } from './banner.reducer';

export const Banner = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const bannerList = useAppSelector(state => state.banner.entities);
  const loading = useAppSelector(state => state.banner.loading);
  const links = useAppSelector(state => state.banner.links);
  const updateSuccess = useAppSelector(state => state.banner.updateSuccess);

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
      <h2 id="banner-heading" data-cy="BannerHeading">
        <Translate contentKey="restaurantApp.banner.home.title">Banners</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="restaurantApp.banner.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/banner/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="restaurantApp.banner.home.createLabel">Create new Banner</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bannerList ? bannerList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bannerList && bannerList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="restaurantApp.banner.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('title')}>
                    <Translate contentKey="restaurantApp.banner.title">Title</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                  </th>
                  <th className="hand" onClick={sort('imageUrl')}>
                    <Translate contentKey="restaurantApp.banner.imageUrl">Image Url</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('imageUrl')} />
                  </th>
                  <th className="hand" onClick={sort('bannerLink')}>
                    <Translate contentKey="restaurantApp.banner.bannerLink">Banner Link</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bannerLink')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="restaurantApp.banner.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('startDate')}>
                    <Translate contentKey="restaurantApp.banner.startDate">Start Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('startDate')} />
                  </th>
                  <th className="hand" onClick={sort('endDate')}>
                    <Translate contentKey="restaurantApp.banner.endDate">End Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('endDate')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="restaurantApp.banner.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bannerList.map((banner, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/banner/${banner.id}`} color="link" size="sm">
                        {banner.id}
                      </Button>
                    </td>
                    <td>{banner.title}</td>
                    <td>{banner.imageUrl}</td>
                    <td>{banner.bannerLink}</td>
                    <td>{banner.description}</td>
                    <td>{banner.startDate ? <TextFormat type="date" value={banner.startDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{banner.endDate ? <TextFormat type="date" value={banner.endDate} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{banner.isActive ? 'true' : 'false'}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/banner/${banner.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/banner/${banner.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (location.href = `/banner/${banner.id}/delete`)}
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
                <Translate contentKey="restaurantApp.banner.home.notFound">No Banners found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Banner;
