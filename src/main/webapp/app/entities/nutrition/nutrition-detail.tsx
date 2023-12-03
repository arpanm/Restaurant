import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nutrition.reducer';

export const NutritionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nutritionEntity = useAppSelector(state => state.nutrition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nutritionDetailsHeading">
          <Translate contentKey="restaurantApp.nutrition.detail.title">Nutrition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.id}</dd>
          <dt>
            <span id="nutritionValue">
              <Translate contentKey="restaurantApp.nutrition.nutritionValue">Nutrition Value</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.nutritionValue}</dd>
          <dt>
            <span id="nutriType">
              <Translate contentKey="restaurantApp.nutrition.nutriType">Nutri Type</Translate>
            </span>
          </dt>
          <dd>{nutritionEntity.nutriType}</dd>
        </dl>
        <Button tag={Link} to="/nutrition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nutrition/${nutritionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NutritionDetail;
