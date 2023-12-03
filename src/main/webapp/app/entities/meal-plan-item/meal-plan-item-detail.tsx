import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './meal-plan-item.reducer';

export const MealPlanItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mealPlanItemEntity = useAppSelector(state => state.mealPlanItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mealPlanItemDetailsHeading">
          <Translate contentKey="restaurantApp.mealPlanItem.detail.title">MealPlanItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mealPlanItemEntity.id}</dd>
          <dt>
            <span id="planItemName">
              <Translate contentKey="restaurantApp.mealPlanItem.planItemName">Plan Item Name</Translate>
            </span>
          </dt>
          <dd>{mealPlanItemEntity.planItemName}</dd>
          <dt>
            <span id="hourValue">
              <Translate contentKey="restaurantApp.mealPlanItem.hourValue">Hour Value</Translate>
            </span>
          </dt>
          <dd>{mealPlanItemEntity.hourValue}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="restaurantApp.mealPlanItem.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{mealPlanItemEntity.pincode}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlanItem.deliveryLocation">Delivery Location</Translate>
          </dt>
          <dd>{mealPlanItemEntity.deliveryLocation ? mealPlanItemEntity.deliveryLocation.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlanItem.items">Items</Translate>
          </dt>
          <dd>
            {mealPlanItemEntity.items
              ? mealPlanItemEntity.items.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {mealPlanItemEntity.items && i === mealPlanItemEntity.items.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="restaurantApp.mealPlanItem.plan">Plan</Translate>
          </dt>
          <dd>{mealPlanItemEntity.plan ? mealPlanItemEntity.plan.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/meal-plan-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/meal-plan-item/${mealPlanItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MealPlanItemDetail;
