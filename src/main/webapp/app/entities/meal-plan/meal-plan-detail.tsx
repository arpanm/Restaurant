import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meal-plan.reducer';

export const MealPlanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mealPlanEntity = useAppSelector(state => state.mealPlan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mealPlanDetailsHeading">
          <Translate contentKey="restaurantApp.mealPlan.detail.title">MealPlan</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mealPlanEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="restaurantApp.mealPlan.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{mealPlanEntity.startDate ? <TextFormat value={mealPlanEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="restaurantApp.mealPlan.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{mealPlanEntity.endDate ? <TextFormat value={mealPlanEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="planName">
              <Translate contentKey="restaurantApp.mealPlan.planName">Plan Name</Translate>
            </span>
          </dt>
          <dd>{mealPlanEntity.planName}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlan.discount">Discount</Translate>
          </dt>
          <dd>{mealPlanEntity.discount ? mealPlanEntity.discount.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlan.mealPlanSetting">Meal Plan Setting</Translate>
          </dt>
          <dd>{mealPlanEntity.mealPlanSetting ? mealPlanEntity.mealPlanSetting.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlan.usr">Usr</Translate>
          </dt>
          <dd>{mealPlanEntity.usr ? mealPlanEntity.usr.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/meal-plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meal-plan/${mealPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MealPlanDetail;
