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
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantType } from 'app/shared/model/enumerations/restaurant-type.model';
import { getEntity, updateEntity, createEntity, reset } from './restaurant.reducer';

export const RestaurantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locations = useAppSelector(state => state.location.entities);
  const restaurantEntity = useAppSelector(state => state.restaurant.entity);
  const loading = useAppSelector(state => state.restaurant.loading);
  const updating = useAppSelector(state => state.restaurant.updating);
  const updateSuccess = useAppSelector(state => state.restaurant.updateSuccess);
  const restaurantTypeValues = Object.keys(RestaurantType);

  const handleClose = () => {
    navigate('/restaurant');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getLocations({}));
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
      ...restaurantEntity,
      ...values,
      location: locations.find(it => it.id.toString() === values.location.toString()),
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
          type: 'MultiCusion',
          ...restaurantEntity,
          location: restaurantEntity?.location?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.restaurant.home.createOrEditLabel" data-cy="RestaurantCreateUpdateHeading">
            <Translate contentKey="restaurantApp.restaurant.home.createOrEditLabel">Create or edit a Restaurant</Translate>
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
                  id="restaurant-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.restaurant.restaurantName')}
                id="restaurant-restaurantName"
                name="restaurantName"
                data-cy="restaurantName"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.restaurant.title')}
                id="restaurant-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.restaurant.logo')}
                id="restaurant-logo"
                name="logo"
                data-cy="logo"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.restaurant.type')}
                id="restaurant-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {restaurantTypeValues.map(restaurantType => (
                  <option value={restaurantType} key={restaurantType}>
                    {translate('restaurantApp.RestaurantType.' + restaurantType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="restaurant-location"
                name="location"
                data-cy="location"
                label={translate('restaurantApp.restaurant.location')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/restaurant" replace color="info">
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

export default RestaurantUpdate;
