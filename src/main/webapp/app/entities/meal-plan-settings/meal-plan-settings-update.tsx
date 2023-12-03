import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWeightInfo } from 'app/shared/model/weight-info.model';
import { getEntities as getWeightInfos } from 'app/entities/weight-info/weight-info.reducer';
import { ICalorieInfo } from 'app/shared/model/calorie-info.model';
import { getEntities as getCalorieInfos } from 'app/entities/calorie-info/calorie-info.reducer';
import { IAvoid } from 'app/shared/model/avoid.model';
import { getEntities as getAvoids } from 'app/entities/avoid/avoid.reducer';
import { IMealPlanSettings } from 'app/shared/model/meal-plan-settings.model';
import { DietType } from 'app/shared/model/enumerations/diet-type.model';
import { FoodType } from 'app/shared/model/enumerations/food-type.model';
import { getEntity, updateEntity, createEntity, reset } from './meal-plan-settings.reducer';

export const MealPlanSettingsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const weightInfos = useAppSelector(state => state.weightInfo.entities);
  const calorieInfos = useAppSelector(state => state.calorieInfo.entities);
  const avoids = useAppSelector(state => state.avoid.entities);
  const mealPlanSettingsEntity = useAppSelector(state => state.mealPlanSettings.entity);
  const loading = useAppSelector(state => state.mealPlanSettings.loading);
  const updating = useAppSelector(state => state.mealPlanSettings.updating);
  const updateSuccess = useAppSelector(state => state.mealPlanSettings.updateSuccess);
  const dietTypeValues = Object.keys(DietType);
  const foodTypeValues = Object.keys(FoodType);

  const handleClose = () => {
    navigate('/meal-plan-settings');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getWeightInfos({}));
    dispatch(getCalorieInfos({}));
    dispatch(getAvoids({}));
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

    const entity = {
      ...mealPlanSettingsEntity,
      ...values,
      avoidLists: mapIdList(values.avoidLists),
      weightInfo: weightInfos.find(it => it.id.toString() === values.weightInfo.toString()),
      calorieInfo: calorieInfos.find(it => it.id.toString() === values.calorieInfo.toString()),
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
          dietType: 'Keto',
          foodType: 'Veg',
          ...mealPlanSettingsEntity,
          weightInfo: mealPlanSettingsEntity?.weightInfo?.id,
          calorieInfo: mealPlanSettingsEntity?.calorieInfo?.id,
          avoidLists: mealPlanSettingsEntity?.avoidLists?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.mealPlanSettings.home.createOrEditLabel" data-cy="MealPlanSettingsCreateUpdateHeading">
            <Translate contentKey="restaurantApp.mealPlanSettings.home.createOrEditLabel">Create or edit a MealPlanSettings</Translate>
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
                  id="meal-plan-settings-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.mealPlanSettings.dietType')}
                id="meal-plan-settings-dietType"
                name="dietType"
                data-cy="dietType"
                type="select"
              >
                {dietTypeValues.map(dietType => (
                  <option value={dietType} key={dietType}>
                    {translate('restaurantApp.DietType.' + dietType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.mealPlanSettings.foodType')}
                id="meal-plan-settings-foodType"
                name="foodType"
                data-cy="foodType"
                type="select"
              >
                {foodTypeValues.map(foodType => (
                  <option value={foodType} key={foodType}>
                    {translate('restaurantApp.FoodType.' + foodType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="meal-plan-settings-weightInfo"
                name="weightInfo"
                data-cy="weightInfo"
                label={translate('restaurantApp.mealPlanSettings.weightInfo')}
                type="select"
              >
                <option value="" key="0" />
                {weightInfos
                  ? weightInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="meal-plan-settings-calorieInfo"
                name="calorieInfo"
                data-cy="calorieInfo"
                label={translate('restaurantApp.mealPlanSettings.calorieInfo')}
                type="select"
              >
                <option value="" key="0" />
                {calorieInfos
                  ? calorieInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.mealPlanSettings.avoidList')}
                id="meal-plan-settings-avoidList"
                data-cy="avoidList"
                type="select"
                multiple
                name="avoidLists"
              >
                <option value="" key="0" />
                {avoids
                  ? avoids.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/meal-plan-settings" replace color="info">
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

export default MealPlanSettingsUpdate;
