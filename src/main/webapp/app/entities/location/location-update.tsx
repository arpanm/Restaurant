import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApplicationUser } from 'app/shared/model/application-user.model';
import { getEntities as getApplicationUsers } from 'app/entities/application-user/application-user.reducer';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { getEntities as getMealPlanItems } from 'app/entities/meal-plan-item/meal-plan-item.reducer';
import { ILocation } from 'app/shared/model/location.model';
import { getEntity, updateEntity, createEntity, reset } from './location.reducer';

export const LocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const applicationUsers = useAppSelector(state => state.applicationUser.entities);
  const restaurants = useAppSelector(state => state.restaurant.entities);
  const mealPlanItems = useAppSelector(state => state.mealPlanItem.entities);
  const locationEntity = useAppSelector(state => state.location.entity);
  const loading = useAppSelector(state => state.location.loading);
  const updating = useAppSelector(state => state.location.updating);
  const updateSuccess = useAppSelector(state => state.location.updateSuccess);

  const handleClose = () => {
    navigate('/location');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getApplicationUsers({}));
    dispatch(getRestaurants({}));
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
    if (values.latitude !== undefined && typeof values.latitude !== 'number') {
      values.latitude = Number(values.latitude);
    }
    if (values.longitude !== undefined && typeof values.longitude !== 'number') {
      values.longitude = Number(values.longitude);
    }

    const entity = {
      ...locationEntity,
      ...values,
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
      ? {}
      : {
          ...locationEntity,
          usr: locationEntity?.usr?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.location.home.createOrEditLabel" data-cy="LocationCreateUpdateHeading">
            <Translate contentKey="restaurantApp.location.home.createOrEditLabel">Create or edit a Location</Translate>
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
                  id="location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.location.saveAs')}
                id="location-saveAs"
                name="saveAs"
                data-cy="saveAs"
                type="text"
              />
              <ValidatedField label={translate('restaurantApp.location.gst')} id="location-gst" name="gst" data-cy="gst" type="text" />
              <ValidatedField label={translate('restaurantApp.location.pan')} id="location-pan" name="pan" data-cy="pan" type="text" />
              <ValidatedField
                label={translate('restaurantApp.location.email')}
                id="location-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.location.phone')}
                id="location-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.location.streetAddress')}
                id="location-streetAddress"
                name="streetAddress"
                data-cy="streetAddress"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.location.postalCode')}
                id="location-postalCode"
                name="postalCode"
                data-cy="postalCode"
                type="text"
              />
              <ValidatedField label={translate('restaurantApp.location.area')} id="location-area" name="area" data-cy="area" type="text" />
              <ValidatedField label={translate('restaurantApp.location.city')} id="location-city" name="city" data-cy="city" type="text" />
              <ValidatedField
                label={translate('restaurantApp.location.stateProvince')}
                id="location-stateProvince"
                name="stateProvince"
                data-cy="stateProvince"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.location.country')}
                id="location-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.location.latitude')}
                id="location-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.location.longitude')}
                id="location-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField id="location-usr" name="usr" data-cy="usr" label={translate('restaurantApp.location.usr')} type="select">
                <option value="" key="0" />
                {applicationUsers
                  ? applicationUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location" replace color="info">
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

export default LocationUpdate;
