import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './food-category.reducer';

export const FoodCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const foodCategoryEntity = useAppSelector(state => state.foodCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="foodCategoryDetailsHeading">
          <Translate contentKey="restaurantApp.foodCategory.detail.title">FoodCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{foodCategoryEntity.id}</dd>
          <dt>
            <span id="catName">
              <Translate contentKey="restaurantApp.foodCategory.catName">Cat Name</Translate>
            </span>
          </dt>
          <dd>{foodCategoryEntity.catName}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="restaurantApp.foodCategory.description">Description</Translate>
            </span>
          </dt>
          <dd>{foodCategoryEntity.description}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="restaurantApp.foodCategory.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{foodCategoryEntity.imageUrl}</dd>
        </dl>
        <Button tag={Link} to="/food-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/food-category/${foodCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FoodCategoryDetail;
