import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrder } from 'app/shared/model/order.model';
import { getEntities as getOrders } from 'app/entities/order/order.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { PaymentVendor } from 'app/shared/model/enumerations/payment-vendor.model';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';
import { getEntity, updateEntity, createEntity, reset } from './payment.reducer';

export const PaymentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const orders = useAppSelector(state => state.order.entities);
  const paymentEntity = useAppSelector(state => state.payment.entity);
  const loading = useAppSelector(state => state.payment.loading);
  const updating = useAppSelector(state => state.payment.updating);
  const updateSuccess = useAppSelector(state => state.payment.updateSuccess);
  const paymentStatusValues = Object.keys(PaymentStatus);
  const paymentVendorValues = Object.keys(PaymentVendor);
  const paymentTypeValues = Object.keys(PaymentType);

  const handleClose = () => {
    navigate('/payment');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }
    values.initDate = convertDateTimeToServer(values.initDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);
    if (values.totalRefundAmount !== undefined && typeof values.totalRefundAmount !== 'number') {
      values.totalRefundAmount = Number(values.totalRefundAmount);
    }

    const entity = {
      ...paymentEntity,
      ...values,
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
      ? {
          initDate: displayDefaultDateTime(),
          updatedDate: displayDefaultDateTime(),
        }
      : {
          status: 'Failed',
          vendor: 'RazorPay',
          type: 'COD',
          ...paymentEntity,
          initDate: convertDateTimeFromServer(paymentEntity.initDate),
          updatedDate: convertDateTimeFromServer(paymentEntity.updatedDate),
          order: paymentEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.payment.home.createOrEditLabel" data-cy="PaymentCreateUpdateHeading">
            <Translate contentKey="restaurantApp.payment.home.createOrEditLabel">Create or edit a Payment</Translate>
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
                  id="payment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.payment.status')}
                id="payment-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate('restaurantApp.PaymentStatus.' + paymentStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.payment.vendor')}
                id="payment-vendor"
                name="vendor"
                data-cy="vendor"
                type="select"
              >
                {paymentVendorValues.map(paymentVendor => (
                  <option value={paymentVendor} key={paymentVendor}>
                    {translate('restaurantApp.PaymentVendor.' + paymentVendor)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('restaurantApp.payment.type')} id="payment-type" name="type" data-cy="type" type="select">
                {paymentTypeValues.map(paymentType => (
                  <option value={paymentType} key={paymentType}>
                    {translate('restaurantApp.PaymentType.' + paymentType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.payment.amount')}
                id="payment-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.payment.initDate')}
                id="payment-initDate"
                name="initDate"
                data-cy="initDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('restaurantApp.payment.updatedDate')}
                id="payment-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('restaurantApp.payment.totalRefundAmount')}
                id="payment-totalRefundAmount"
                name="totalRefundAmount"
                data-cy="totalRefundAmount"
                type="text"
              />
              <ValidatedField
                id="payment-order"
                name="order"
                data-cy="order"
                label={translate('restaurantApp.payment.order')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment" replace color="info">
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

export default PaymentUpdate;
