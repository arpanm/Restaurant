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
import { ICalorieInfo } from 'app/shared/model/calorie-info.model';
import { getEntity, updateEntity, createEntity, reset } from './calorie-info.reducer';

export const CalorieInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mealPlanSettings = useAppSelector(state => state.mealPlanSettings.entities);
  const calorieInfoEntity = useAppSelector(state => state.calorieInfo.entity);
  const loading = useAppSelector(state => state.calorieInfo.loading);
  const updating = useAppSelector(state => state.calorieInfo.updating);
  const updateSuccess = useAppSelector(state => state.calorieInfo.updateSuccess);

  const handleClose = () => {
    navigate('/calorie-info');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getMealPlanSettings({}));
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
    if (values.dailyCalorieLimit !== undefined && typeof values.dailyCalorieLimit !== 'number') {
      values.dailyCalorieLimit = Number(values.dailyCalorieLimit);
    }

    const entity = {
      ...calorieInfoEntity,
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
          ...calorieInfoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.calorieInfo.home.createOrEditLabel" data-cy="CalorieInfoCreateUpdateHeading">
            <Translate contentKey="restaurantApp.calorieInfo.home.createOrEditLabel">Create or edit a CalorieInfo</Translate>
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
                  id="calorie-info-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.calorieInfo.dailyCalorieLimit')}
                id="calorie-info-dailyCalorieLimit"
                name="dailyCalorieLimit"
                data-cy="dailyCalorieLimit"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/calorie-info" replace color="info">
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

export default CalorieInfoUpdate;
