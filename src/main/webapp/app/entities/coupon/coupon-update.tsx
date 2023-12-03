import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICart } from 'app/shared/model/cart.model';
import { getEntities as getCarts } from 'app/entities/cart/cart.reducer';
import { ICoupon } from 'app/shared/model/coupon.model';
import { getEntity, updateEntity, createEntity, reset } from './coupon.reducer';

export const CouponUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carts = useAppSelector(state => state.cart.entities);
  const couponEntity = useAppSelector(state => state.coupon.entity);
  const loading = useAppSelector(state => state.coupon.loading);
  const updating = useAppSelector(state => state.coupon.updating);
  const updateSuccess = useAppSelector(state => state.coupon.updateSuccess);

  const handleClose = () => {
    navigate('/coupon');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCarts({}));
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
    if (values.maxValue !== undefined && typeof values.maxValue !== 'number') {
      values.maxValue = Number(values.maxValue);
    }
    if (values.maxPercentage !== undefined && typeof values.maxPercentage !== 'number') {
      values.maxPercentage = Number(values.maxPercentage);
    }
    if (values.minOrderValue !== undefined && typeof values.minOrderValue !== 'number') {
      values.minOrderValue = Number(values.minOrderValue);
    }

    const entity = {
      ...couponEntity,
      ...values,
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
          ...couponEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.coupon.home.createOrEditLabel" data-cy="CouponCreateUpdateHeading">
            <Translate contentKey="restaurantApp.coupon.home.createOrEditLabel">Create or edit a Coupon</Translate>
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
                  id="coupon-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.coupon.maxValue')}
                id="coupon-maxValue"
                name="maxValue"
                data-cy="maxValue"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.coupon.maxPercentage')}
                id="coupon-maxPercentage"
                name="maxPercentage"
                data-cy="maxPercentage"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.coupon.minOrderValue')}
                id="coupon-minOrderValue"
                name="minOrderValue"
                data-cy="minOrderValue"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/coupon" replace color="info">
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

export default CouponUpdate;
