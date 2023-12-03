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
import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { getEntities as getMealPlans } from 'app/entities/meal-plan/meal-plan.reducer';
import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { getEntity, updateEntity, createEntity, reset } from './meal-plan-item.reducer';

export const MealPlanItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locations = useAppSelector(state => state.location.entities);
  const items = useAppSelector(state => state.item.entities);
  const mealPlans = useAppSelector(state => state.mealPlan.entities);
  const mealPlanItemEntity = useAppSelector(state => state.mealPlanItem.entity);
  const loading = useAppSelector(state => state.mealPlanItem.loading);
  const updating = useAppSelector(state => state.mealPlanItem.updating);
  const updateSuccess = useAppSelector(state => state.mealPlanItem.updateSuccess);

  const handleClose = () => {
    navigate('/meal-plan-item');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getLocations({}));
    dispatch(getItems({}));
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
    if (values.hourValue !== undefined && typeof values.hourValue !== 'number') {
      values.hourValue = Number(values.hourValue);
    }

    const entity = {
      ...mealPlanItemEntity,
      ...values,
      items: mapIdList(values.items),
      deliveryLocation: locations.find(it => it.id.toString() === values.deliveryLocation.toString()),
      plan: mealPlans.find(it => it.id.toString() === values.plan.toString()),
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
          ...mealPlanItemEntity,
          deliveryLocation: mealPlanItemEntity?.deliveryLocation?.id,
          items: mealPlanItemEntity?.items?.map(e => e.id.toString()),
          plan: mealPlanItemEntity?.plan?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.mealPlanItem.home.createOrEditLabel" data-cy="MealPlanItemCreateUpdateHeading">
            <Translate contentKey="restaurantApp.mealPlanItem.home.createOrEditLabel">Create or edit a MealPlanItem</Translate>
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
                  id="meal-plan-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.mealPlanItem.planItemName')}
                id="meal-plan-item-planItemName"
                name="planItemName"
                data-cy="planItemName"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.mealPlanItem.hourValue')}
                id="meal-plan-item-hourValue"
                name="hourValue"
                data-cy="hourValue"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.mealPlanItem.pincode')}
                id="meal-plan-item-pincode"
                name="pincode"
                data-cy="pincode"
                type="text"
              />
              <ValidatedField
                id="meal-plan-item-deliveryLocation"
                name="deliveryLocation"
                data-cy="deliveryLocation"
                label={translate('restaurantApp.mealPlanItem.deliveryLocation')}
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
                label={translate('restaurantApp.mealPlanItem.items')}
                id="meal-plan-item-items"
                data-cy="items"
                type="select"
                multiple
                name="items"
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
                id="meal-plan-item-plan"
                name="plan"
                data-cy="plan"
                label={translate('restaurantApp.mealPlanItem.plan')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/meal-plan-item" replace color="info">
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

export default MealPlanItemUpdate;
