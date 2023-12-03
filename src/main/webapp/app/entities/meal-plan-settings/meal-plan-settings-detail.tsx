import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meal-plan-settings.reducer';

export const MealPlanSettingsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mealPlanSettingsEntity = useAppSelector(state => state.mealPlanSettings.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mealPlanSettingsDetailsHeading">
          <Translate contentKey="restaurantApp.mealPlanSettings.detail.title">MealPlanSettings</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mealPlanSettingsEntity.id}</dd>
          <dt>
            <span id="dietType">
              <Translate contentKey="restaurantApp.mealPlanSettings.dietType">Diet Type</Translate>
            </span>
          </dt>
          <dd>{mealPlanSettingsEntity.dietType}</dd>
          <dt>
            <span id="foodType">
              <Translate contentKey="restaurantApp.mealPlanSettings.foodType">Food Type</Translate>
            </span>
          </dt>
          <dd>{mealPlanSettingsEntity.foodType}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlanSettings.weightInfo">Weight Info</Translate>
          </dt>
          <dd>{mealPlanSettingsEntity.weightInfo ? mealPlanSettingsEntity.weightInfo.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlanSettings.calorieInfo">Calorie Info</Translate>
          </dt>
          <dd>{mealPlanSettingsEntity.calorieInfo ? mealPlanSettingsEntity.calorieInfo.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlanSettings.avoidList">Avoid List</Translate>
          </dt>
          <dd>
            {mealPlanSettingsEntity.avoidLists
              ? mealPlanSettingsEntity.avoidLists.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {mealPlanSettingsEntity.avoidLists && i === mealPlanSettingsEntity.avoidLists.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/meal-plan-settings" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meal-plan-settings/${mealPlanSettingsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MealPlanSettingsDetail;
