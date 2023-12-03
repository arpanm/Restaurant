import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { getEntities as getMealPlanItems } from 'app/entities/meal-plan-item/meal-plan-item.reducer';
import { ISkipDate } from 'app/shared/model/skip-date.model';
import { getEntity, updateEntity, createEntity, reset } from './skip-date.reducer';

export const SkipDateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mealPlanItems = useAppSelector(state => state.mealPlanItem.entities);
  const skipDateEntity = useAppSelector(state => state.skipDate.entity);
  const loading = useAppSelector(state => state.skipDate.loading);
  const updating = useAppSelector(state => state.skipDate.updating);
  const updateSuccess = useAppSelector(state => state.skipDate.updateSuccess);

  const handleClose = () => {
    navigate('/skip-date');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getMealPlanItems({}));
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
      ...skipDateEntity,
      ...values,
      mealPlanItem: mealPlanItems.find(it => it.id.toString() === values.mealPlanItem.toString()),
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
          ...skipDateEntity,
          mealPlanItem: skipDateEntity?.mealPlanItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.skipDate.home.createOrEditLabel" data-cy="SkipDateCreateUpdateHeading">
            <Translate contentKey="restaurantApp.skipDate.home.createOrEditLabel">Create or edit a SkipDate</Translate>
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
                  id="skip-date-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.skipDate.skipDate')}
                id="skip-date-skipDate"
                name="skipDate"
                data-cy="skipDate"
                type="date"
              />
              <ValidatedField
                id="skip-date-mealPlanItem"
                name="mealPlanItem"
                data-cy="mealPlanItem"
                label={translate('restaurantApp.skipDate.mealPlanItem')}
                type="select"
              >
                <option value="" key="0" />
                {mealPlanItems
                  ? mealPlanItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/skip-date" replace color="info">
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

export default SkipDateUpdate;
