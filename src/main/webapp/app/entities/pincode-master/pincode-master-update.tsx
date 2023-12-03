import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPincodeMaster } from 'app/shared/model/pincode-master.model';
import { getEntity, updateEntity, createEntity, reset } from './pincode-master.reducer';

export const PincodeMasterUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pincodeMasterEntity = useAppSelector(state => state.pincodeMaster.entity);
  const loading = useAppSelector(state => state.pincodeMaster.loading);
  const updating = useAppSelector(state => state.pincodeMaster.updating);
  const updateSuccess = useAppSelector(state => state.pincodeMaster.updateSuccess);

  const handleClose = () => {
    navigate('/pincode-master');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
      ...pincodeMasterEntity,
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
          ...pincodeMasterEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.pincodeMaster.home.createOrEditLabel" data-cy="PincodeMasterCreateUpdateHeading">
            <Translate contentKey="restaurantApp.pincodeMaster.home.createOrEditLabel">Create or edit a PincodeMaster</Translate>
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
                  id="pincode-master-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.pincodeMaster.pincode')}
                id="pincode-master-pincode"
                name="pincode"
                data-cy="pincode"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.pincodeMaster.area')}
                id="pincode-master-area"
                name="area"
                data-cy="area"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.pincodeMaster.city')}
                id="pincode-master-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.pincodeMaster.stateProvince')}
                id="pincode-master-stateProvince"
                name="stateProvince"
                data-cy="stateProvince"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.pincodeMaster.country')}
                id="pincode-master-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pincode-master" replace color="info">
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

export default PincodeMasterUpdate;
