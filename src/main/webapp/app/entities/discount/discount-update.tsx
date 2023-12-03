import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { getEntities as getMealPlans } from 'app/entities/meal-plan/meal-plan.reducer';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountSlab } from 'app/shared/model/enumerations/discount-slab.model';
import { getEntity, updateEntity, createEntity, reset } from './discount.reducer';

export const DiscountUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mealPlans = useAppSelector(state => state.mealPlan.entities);
  const discountEntity = useAppSelector(state => state.discount.entity);
  const loading = useAppSelector(state => state.discount.loading);
  const updating = useAppSelector(state => state.discount.updating);
  const updateSuccess = useAppSelector(state => state.discount.updateSuccess);
  const discountSlabValues = Object.keys(DiscountSlab);

  const handleClose = () => {
    navigate('/discount');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getMealPlans({}));
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
    if (values.discount !== undefined && typeof values.discount !== 'number') {
      values.discount = Number(values.discount);
    }

    const entity = {
      ...discountEntity,
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
          slab: 'OneDay',
          ...discountEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.discount.home.createOrEditLabel" data-cy="DiscountCreateUpdateHeading">
            <Translate contentKey="restaurantApp.discount.home.createOrEditLabel">Create or edit a Discount</Translate>
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
                  id="discount-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.discount.discount')}
                id="discount-discount"
                name="discount"
                data-cy="discount"
                type="text"
              />
              <ValidatedField label={translate('restaurantApp.discount.slab')} id="discount-slab" name="slab" data-cy="slab" type="select">
                {discountSlabValues.map(discountSlab => (
                  <option value={discountSlab} key={discountSlab}>
                    {translate('restaurantApp.DiscountSlab.' + discountSlab)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/discount" replace color="info">
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

export default DiscountUpdate;
