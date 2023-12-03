import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { INutrition } from 'app/shared/model/nutrition.model';
import { NutritionType } from 'app/shared/model/enumerations/nutrition-type.model';
import { getEntity, updateEntity, createEntity, reset } from './nutrition.reducer';

export const NutritionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const items = useAppSelector(state => state.item.entities);
  const nutritionEntity = useAppSelector(state => state.nutrition.entity);
  const loading = useAppSelector(state => state.nutrition.loading);
  const updating = useAppSelector(state => state.nutrition.updating);
  const updateSuccess = useAppSelector(state => state.nutrition.updateSuccess);
  const nutritionTypeValues = Object.keys(NutritionType);

  const handleClose = () => {
    navigate('/nutrition');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getItems({}));
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
    if (values.nutritionValue !== undefined && typeof values.nutritionValue !== 'number') {
      values.nutritionValue = Number(values.nutritionValue);
    }

    const entity = {
      ...nutritionEntity,
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
          nutriType: 'Calory',
          ...nutritionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.nutrition.home.createOrEditLabel" data-cy="NutritionCreateUpdateHeading">
            <Translate contentKey="restaurantApp.nutrition.home.createOrEditLabel">Create or edit a Nutrition</Translate>
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
                  id="nutrition-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.nutrition.nutritionValue')}
                id="nutrition-nutritionValue"
                name="nutritionValue"
                data-cy="nutritionValue"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.nutrition.nutriType')}
                id="nutrition-nutriType"
                name="nutriType"
                data-cy="nutriType"
                type="select"
              >
                {nutritionTypeValues.map(nutritionType => (
                  <option value={nutritionType} key={nutritionType}>
                    {translate('restaurantApp.NutritionType.' + nutritionType)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nutrition" replace color="info">
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

export default NutritionUpdate;
