import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPayment } from 'app/shared/model/payment.model';
import { getEntities as getPayments } from 'app/entities/payment/payment.reducer';
import { IRefund } from 'app/shared/model/refund.model';
import { RefundStatus } from 'app/shared/model/enumerations/refund-status.model';
import { PaymentVendor } from 'app/shared/model/enumerations/payment-vendor.model';
import { getEntity, updateEntity, createEntity, reset } from './refund.reducer';

export const RefundUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const payments = useAppSelector(state => state.payment.entities);
  const refundEntity = useAppSelector(state => state.refund.entity);
  const loading = useAppSelector(state => state.refund.loading);
  const updating = useAppSelector(state => state.refund.updating);
  const updateSuccess = useAppSelector(state => state.refund.updateSuccess);
  const refundStatusValues = Object.keys(RefundStatus);
  const paymentVendorValues = Object.keys(PaymentVendor);

  const handleClose = () => {
    navigate('/refund');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getPayments({}));
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

    const entity = {
      ...refundEntity,
      ...values,
      payment: payments.find(it => it.id.toString() === values.payment.toString()),
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
          status: 'Success',
          vendor: 'RazorPay',
          ...refundEntity,
          initDate: convertDateTimeFromServer(refundEntity.initDate),
          updatedDate: convertDateTimeFromServer(refundEntity.updatedDate),
          payment: refundEntity?.payment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.refund.home.createOrEditLabel" data-cy="RefundCreateUpdateHeading">
            <Translate contentKey="restaurantApp.refund.home.createOrEditLabel">Create or edit a Refund</Translate>
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
                  id="refund-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.refund.status')}
                id="refund-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {refundStatusValues.map(refundStatus => (
                  <option value={refundStatus} key={refundStatus}>
                    {translate('restaurantApp.RefundStatus.' + refundStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.refund.vendor')}
                id="refund-vendor"
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
              <ValidatedField
                label={translate('restaurantApp.refund.amount')}
                id="refund-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.refund.initDate')}
                id="refund-initDate"
                name="initDate"
                data-cy="initDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('restaurantApp.refund.updatedDate')}
                id="refund-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="refund-payment"
                name="payment"
                data-cy="payment"
                label={translate('restaurantApp.refund.payment')}
                type="select"
              >
                <option value="" key="0" />
                {payments
                  ? payments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/refund" replace color="info">
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

export default RefundUpdate;
