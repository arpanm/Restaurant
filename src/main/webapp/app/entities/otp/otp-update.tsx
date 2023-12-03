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
import { IOtp } from 'app/shared/model/otp.model';
import { getEntity, updateEntity, createEntity, reset } from './otp.reducer';

export const OtpUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const applicationUsers = useAppSelector(state => state.applicationUser.entities);
  const otpEntity = useAppSelector(state => state.otp.entity);
  const loading = useAppSelector(state => state.otp.loading);
  const updating = useAppSelector(state => state.otp.updating);
  const updateSuccess = useAppSelector(state => state.otp.updateSuccess);

  const handleClose = () => {
    navigate('/otp');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    if (values.otp !== undefined && typeof values.otp !== 'number') {
      values.otp = Number(values.otp);
    }
    values.expiry = convertDateTimeToServer(values.expiry);

    const entity = {
      ...otpEntity,
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
      ? {
          expiry: displayDefaultDateTime(),
        }
      : {
          ...otpEntity,
          expiry: convertDateTimeFromServer(otpEntity.expiry),
          usr: otpEntity?.usr?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.otp.home.createOrEditLabel" data-cy="OtpCreateUpdateHeading">
            <Translate contentKey="restaurantApp.otp.home.createOrEditLabel">Create or edit a Otp</Translate>
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
                  id="otp-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('restaurantApp.otp.email')} id="otp-email" name="email" data-cy="email" type="text" />
              <ValidatedField label={translate('restaurantApp.otp.phone')} id="otp-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField label={translate('restaurantApp.otp.otp')} id="otp-otp" name="otp" data-cy="otp" type="text" />
              <ValidatedField
                label={translate('restaurantApp.otp.isActive')}
                id="otp-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('restaurantApp.otp.isValidated')}
                id="otp-isValidated"
                name="isValidated"
                data-cy="isValidated"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('restaurantApp.otp.expiry')}
                id="otp-expiry"
                name="expiry"
                data-cy="expiry"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="otp-usr" name="usr" data-cy="usr" label={translate('restaurantApp.otp.usr')} type="select">
                <option value="" key="0" />
                {applicationUsers
                  ? applicationUsers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/otp" replace color="info">
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

export default OtpUpdate;
