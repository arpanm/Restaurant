import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { UserRole } from 'app/shared/model/enumerations/user-role.model';
import { getEntity, updateEntity, createEntity, reset } from './application-user.reducer';

export const ApplicationUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const restaurants = useAppSelector(state => state.restaurant.entities);
  const applicationUserEntity = useAppSelector(state => state.applicationUser.entity);
  const loading = useAppSelector(state => state.applicationUser.loading);
  const updating = useAppSelector(state => state.applicationUser.updating);
  const updateSuccess = useAppSelector(state => state.applicationUser.updateSuccess);
  const userRoleValues = Object.keys(UserRole);

  const handleClose = () => {
    navigate('/application-user');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getRestaurants({}));
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
    values.phoneValidatedOn = convertDateTimeToServer(values.phoneValidatedOn);
    values.emailValidatedOn = convertDateTimeToServer(values.emailValidatedOn);

    const entity = {
      ...applicationUserEntity,
      ...values,
      restaurant: restaurants.find(it => it.id.toString() === values.restaurant.toString()),
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
          phoneValidatedOn: displayDefaultDateTime(),
          emailValidatedOn: displayDefaultDateTime(),
        }
      : {
          role: 'Customer',
          ...applicationUserEntity,
          phoneValidatedOn: convertDateTimeFromServer(applicationUserEntity.phoneValidatedOn),
          emailValidatedOn: convertDateTimeFromServer(applicationUserEntity.emailValidatedOn),
          restaurant: applicationUserEntity?.restaurant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.applicationUser.home.createOrEditLabel" data-cy="ApplicationUserCreateUpdateHeading">
            <Translate contentKey="restaurantApp.applicationUser.home.createOrEditLabel">Create or edit a ApplicationUser</Translate>
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
                  id="application-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.applicationUser.name')}
                id="application-user-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.applicationUser.password')}
                id="application-user-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.applicationUser.email')}
                id="application-user-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.applicationUser.phone')}
                id="application-user-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.applicationUser.role')}
                id="application-user-role"
                name="role"
                data-cy="role"
                type="select"
              >
                {userRoleValues.map(userRole => (
                  <option value={userRole} key={userRole}>
                    {translate('restaurantApp.UserRole.' + userRole)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.applicationUser.isPhoneValidated')}
                id="application-user-isPhoneValidated"
                name="isPhoneValidated"
                data-cy="isPhoneValidated"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('restaurantApp.applicationUser.isEmailValidated')}
                id="application-user-isEmailValidated"
                name="isEmailValidated"
                data-cy="isEmailValidated"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('restaurantApp.applicationUser.phoneValidatedOn')}
                id="application-user-phoneValidatedOn"
                name="phoneValidatedOn"
                data-cy="phoneValidatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('restaurantApp.applicationUser.emailValidatedOn')}
                id="application-user-emailValidatedOn"
                name="emailValidatedOn"
                data-cy="emailValidatedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="application-user-restaurant"
                name="restaurant"
                data-cy="restaurant"
                label={translate('restaurantApp.applicationUser.restaurant')}
                type="select"
              >
                <option value="" key="0" />
                {restaurants
                  ? restaurants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/application-user" replace color="info">
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

export default ApplicationUserUpdate;
