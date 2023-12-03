import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './restaurant.reducer';

export const RestaurantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const restaurantEntity = useAppSelector(state => state.restaurant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="restaurantDetailsHeading">
          <Translate contentKey="restaurantApp.restaurant.detail.title">Restaurant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.id}</dd>
          <dt>
            <span id="restaurantName">
              <Translate contentKey="restaurantApp.restaurant.restaurantName">Restaurant Name</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.restaurantName}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="restaurantApp.restaurant.title">Title</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.title}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="restaurantApp.restaurant.logo">Logo</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.logo}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="restaurantApp.restaurant.type">Type</Translate>
            </span>
          </dt>
          <dd>{restaurantEntity.type}</dd>
          <dt>
            <Translate contentKey="restaurantApp.restaurant.location">Location</Translate>
          </dt>
          <dd>{restaurantEntity.location ? restaurantEntity.location.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/restaurant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/restaurant/${restaurantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RestaurantDetail;
