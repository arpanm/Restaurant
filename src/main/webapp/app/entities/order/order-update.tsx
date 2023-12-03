import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { ICoupon } from 'app/shared/model/coupon.model';
import { getEntities as getCoupons } from 'app/entities/coupon/coupon.reducer';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { getEntities as getApplicationUsers } from 'app/entities/application-user/application-user.reducer';
import { IOrder } from 'app/shared/model/order.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { getEntity, updateEntity, createEntity, reset } from './order.reducer';

export const OrderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locations = useAppSelector(state => state.location.entities);
  const coupons = useAppSelector(state => state.coupon.entities);
  const applicationUsers = useAppSelector(state => state.applicationUser.entities);
  const orderEntity = useAppSelector(state => state.order.entity);
  const loading = useAppSelector(state => state.order.loading);
  const updating = useAppSelector(state => state.order.updating);
  const updateSuccess = useAppSelector(state => state.order.updateSuccess);
  const orderStatusValues = Object.keys(OrderStatus);

  const handleClose = () => {
    navigate('/order');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getLocations({}));
    dispatch(getCoupons({}));
    dispatch(getApplicationUsers({}));
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
    values.orderDate = convertDateTimeToServer(values.orderDate);
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }
    if (values.couponValue !== undefined && typeof values.couponValue !== 'number') {
      values.couponValue = Number(values.couponValue);
    }

    const entity = {
      ...orderEntity,
      ...values,
      billingLoc: locations.find(it => it.id.toString() === values.billingLoc.toString()),
      coupon: coupons.find(it => it.id.toString() === values.coupon.toString()),
      usr: applicationUsers.find(it => it.id.toString() === values.usr.toString()),
      statusUpdatedBy: applicationUsers.find(it => it.id.toString() === values.statusUpdatedBy.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          orderDate: displayDefaultDateTime(),
        }
      : {
          status: 'Pending',
          ...orderEntity,
          orderDate: convertDateTimeFromServer(orderEntity.orderDate),
          billingLoc: orderEntity?.billingLoc?.id,
          coupon: orderEntity?.coupon?.id,
          usr: orderEntity?.usr?.id,
          statusUpdatedBy: orderEntity?.statusUpdatedBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.order.home.createOrEditLabel" data-cy="OrderCreateUpdateHeading">
            <Translate contentKey="restaurantApp.order.home.createOrEditLabel">Create or edit a Order</Translate>
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
                  id="order-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.order.status')}
                id="order-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {orderStatusValues.map(orderStatus => (
                  <option value={orderStatus} key={orderStatus}>
                    {translate('restaurantApp.OrderStatus.' + orderStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.order.orderDate')}
                id="order-orderDate"
                name="orderDate"
                data-cy="orderDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('restaurantApp.order.amount')}
                id="order-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.order.couponValue')}
                id="order-couponValue"
                name="couponValue"
                data-cy="couponValue"
                type="text"
              />
              <ValidatedField
                id="order-billingLoc"
                name="billingLoc"
                data-cy="billingLoc"
                label={translate('restaurantApp.order.billingLoc')}
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
                id="order-coupon"
                name="coupon"
                data-cy="coupon"
                label={translate('restaurantApp.order.coupon')}
                type="select"
              >
                <option value="" key="0" />
                {coupons
                  ? coupons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="order-usr" name="usr" data-cy="usr" label={translate('restaurantApp.order.usr')} type="select">
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
                id="order-statusUpdatedBy"
                name="statusUpdatedBy"
                data-cy="statusUpdatedBy"
                label={translate('restaurantApp.order.statusUpdatedBy')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order" replace color="info">
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

export default OrderUpdate;
