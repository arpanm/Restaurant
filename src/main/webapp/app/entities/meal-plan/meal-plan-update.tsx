import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMealPlanSettings } from 'app/shared/model/meal-plan-settings.model';
import { getEntities as getMealPlanSettings } from 'app/entities/meal-plan-settings/meal-plan-settings.reducer';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { getEntities as getApplicationUsers } from 'app/entities/application-user/application-user.reducer';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { getEntity, updateEntity, createEntity, reset } from './meal-plan.reducer';

export const MealPlanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mealPlanSettings = useAppSelector(state => state.mealPlanSettings.entities);
  const applicationUsers = useAppSelector(state => state.applicationUser.entities);
  const mealPlanEntity = useAppSelector(state => state.mealPlan.entity);
  const loading = useAppSelector(state => state.mealPlan.loading);
  const updating = useAppSelector(state => state.mealPlan.updating);
  const updateSuccess = useAppSelector(state => state.mealPlan.updateSuccess);

  const handleClose = () => {
    navigate('/meal-plan');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getMealPlanSettings({}));
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
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    const entity = {
      ...mealPlanEntity,
      ...values,
      mealPlanSetting: mealPlanSettings.find(it => it.id.toString() === values.mealPlanSetting.toString()),
      usr: applicationUsers.find(it => it.id.toString() === values.usr.toString()),
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
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          ...mealPlanEntity,
          startDate: convertDateTimeFromServer(mealPlanEntity.startDate),
          endDate: convertDateTimeFromServer(mealPlanEntity.endDate),
          mealPlanSetting: mealPlanEntity?.mealPlanSetting?.id,
          usr: mealPlanEntity?.usr?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.mealPlan.home.createOrEditLabel" data-cy="MealPlanCreateUpdateHeading">
            <Translate contentKey="restaurantApp.mealPlan.home.createOrEditLabel">Create or edit a MealPlan</Translate>
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
                  id="meal-plan-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.mealPlan.startDate')}
                id="meal-plan-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('restaurantApp.mealPlan.endDate')}
                id="meal-plan-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('restaurantApp.mealPlan.planName')}
                id="meal-plan-planName"
                name="planName"
                data-cy="planName"
                type="text"
              />
              <ValidatedField
                id="meal-plan-mealPlanSetting"
                name="mealPlanSetting"
                data-cy="mealPlanSetting"
                label={translate('restaurantApp.mealPlan.mealPlanSetting')}
                type="select"
              >
                <option value="" key="0" />
                {mealPlanSettings
                  ? mealPlanSettings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="meal-plan-usr" name="usr" data-cy="usr" label={translate('restaurantApp.mealPlan.usr')} type="select">
                <option value="" key="0" />
                {applicationUsers
                  ? applicationUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/meal-plan" replace color="info">
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

export default MealPlanUpdate;
