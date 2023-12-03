import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { getEntities as getMealPlans } from 'app/entities/meal-plan/meal-plan.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { getEntities as getApplicationUsers } from 'app/entities/application-user/application-user.reducer';
import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderItemStatus } from 'app/shared/model/enumerations/order-item-status.model';
import { getEntity, updateEntity, createEntity, reset } from './order-item.reducer';

export const OrderItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const items = useAppSelector(state => state.item.entities);
  const mealPlans = useAppSelector(state => state.mealPlan.entities);
  const locations = useAppSelector(state => state.location.entities);
  const restaurants = useAppSelector(state => state.restaurant.entities);
  const applicationUsers = useAppSelector(state => state.applicationUser.entities);
  const orders = useAppSelector(state => state.order.entities);
  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  const loading = useAppSelector(state => state.orderItem.loading);
  const updating = useAppSelector(state => state.orderItem.updating);
  const updateSuccess = useAppSelector(state => state.orderItem.updateSuccess);
  const orderItemStatusValues = Object.keys(OrderItemStatus);

  const handleClose = () => {
    navigate('/order-item');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getItems({}));
    dispatch(getMealPlans({}));
    dispatch(getLocations({}));
    dispatch(getRestaurants({}));
    dispatch(getApplicationUsers({}));
    dispatch(getOrders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.quantity !== undefined && typeof values.quantity !== 'number') {
      values.quantity = Number(values.quantity);
    }

    const entity = {
      ...orderItemEntity,
      ...values,
      item: items.find(it => it.id.toString() === values.item.toString()),
      meal: mealPlans.find(it => it.id.toString() === values.meal.toString()),
      deliveryLoc: locations.find(it => it.id.toString() === values.deliveryLoc.toString()),
      restaurant: restaurants.find(it => it.id.toString() === values.restaurant.toString()),
      orderAssignedTo: applicationUsers.find(it => it.id.toString() === values.orderAssignedTo.toString()),
      order: orders.find(it => it.id.toString() === values.order.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          status: 'Pending',
          ...orderItemEntity,
          item: orderItemEntity?.item?.id,
          meal: orderItemEntity?.meal?.id,
          deliveryLoc: orderItemEntity?.deliveryLoc?.id,
          restaurant: orderItemEntity?.restaurant?.id,
          orderAssignedTo: orderItemEntity?.orderAssignedTo?.id,
          order: orderItemEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.orderItem.home.createOrEditLabel" data-cy="OrderItemCreateUpdateHeading">
            <Translate contentKey="restaurantApp.orderItem.home.createOrEditLabel">Create or edit a OrderItem</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="order-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.orderItem.quantity')}
                id="order-item-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.orderItem.status')}
                id="order-item-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {orderItemStatusValues.map(orderItemStatus => (
                  <option value={orderItemStatus} key={orderItemStatus}>
                    {translate('restaurantApp.OrderItemStatus.' + orderItemStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="order-item-item"
                name="item"
                data-cy="item"
                label={translate('restaurantApp.orderItem.item')}
                type="select"
              >
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-item-meal"
                name="meal"
                data-cy="meal"
                label={translate('restaurantApp.orderItem.meal')}
                type="select"
              >
                <option value="" key="0" />
                {mealPlans
                  ? mealPlans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-item-deliveryLoc"
                name="deliveryLoc"
                data-cy="deliveryLoc"
                label={translate('restaurantApp.orderItem.deliveryLoc')}
                type="select"
              >
                <option value="" key="0" />
                {locations
                  ? locations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-item-restaurant"
                name="restaurant"
                data-cy="restaurant"
                label={translate('restaurantApp.orderItem.restaurant')}
                type="select"
              >
                <option value="" key="0" />
                {restaurants
                  ? restaurants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-item-orderAssignedTo"
                name="orderAssignedTo"
                data-cy="orderAssignedTo"
                label={translate('restaurantApp.orderItem.orderAssignedTo')}
                type="select"
              >
                <option value="" key="0" />
                {applicationUsers
                  ? applicationUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="order-item-order"
                name="order"
                data-cy="order"
                label={translate('restaurantApp.orderItem.order')}
                type="select"
              >
                <option value="" key="0" />
                {orders
                  ? orders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order-item" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrderItemUpdate;
