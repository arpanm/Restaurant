import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './banner.reducer';

export const BannerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bannerEntity = useAppSelector(state => state.banner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bannerDetailsHeading">
          <Translate contentKey="restaurantApp.banner.detail.title">Banner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="restaurantApp.banner.title">Title</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.title}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="restaurantApp.banner.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.imageUrl}</dd>
          <dt>
            <span id="bannerLink">
              <Translate contentKey="restaurantApp.banner.bannerLink">Banner Link</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.bannerLink}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="restaurantApp.banner.description">Description</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.description}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="restaurantApp.banner.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.startDate ? <TextFormat value={bannerEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="restaurantApp.banner.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.endDate ? <TextFormat value={bannerEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="restaurantApp.banner.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{bannerEntity.isActive ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/banner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banner/${bannerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BannerDetail;
