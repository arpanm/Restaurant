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
import { IWeightInfo } from 'app/shared/model/weight-info.model';
import { getEntity, updateEntity, createEntity, reset } from './weight-info.reducer';

export const WeightInfoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mealPlanSettings = useAppSelector(state => state.mealPlanSettings.entities);
  const weightInfoEntity = useAppSelector(state => state.weightInfo.entity);
  const loading = useAppSelector(state => state.weightInfo.loading);
  const updating = useAppSelector(state => state.weightInfo.updating);
  const updateSuccess = useAppSelector(state => state.weightInfo.updateSuccess);

  const handleClose = () => {
    navigate('/weight-info');
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
    if (values.currentWeight !== undefined && typeof values.currentWeight !== 'number') {
      values.currentWeight = Number(values.currentWeight);
    }
    if (values.expectedWeight !== undefined && typeof values.expectedWeight !== 'number') {
      values.expectedWeight = Number(values.expectedWeight);
    }
    if (values.heightInInch !== undefined && typeof values.heightInInch !== 'number') {
      values.heightInInch = Number(values.heightInInch);
    }
    if (values.numberOfDays !== undefined && typeof values.numberOfDays !== 'number') {
      values.numberOfDays = Number(values.numberOfDays);
    }

    const entity = {
      ...weightInfoEntity,
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
          ...weightInfoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.weightInfo.home.createOrEditLabel" data-cy="WeightInfoCreateUpdateHeading">
            <Translate contentKey="restaurantApp.weightInfo.home.createOrEditLabel">Create or edit a WeightInfo</Translate>
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
                  id="weight-info-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.weightInfo.currentWeight')}
                id="weight-info-currentWeight"
                name="currentWeight"
                data-cy="currentWeight"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.weightInfo.expectedWeight')}
                id="weight-info-expectedWeight"
                name="expectedWeight"
                data-cy="expectedWeight"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.weightInfo.heightInInch')}
                id="weight-info-heightInInch"
                name="heightInInch"
                data-cy="heightInInch"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.weightInfo.numberOfDays')}
                id="weight-info-numberOfDays"
                name="numberOfDays"
                data-cy="numberOfDays"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/weight-info" replace color="info">
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

export default WeightInfoUpdate;
