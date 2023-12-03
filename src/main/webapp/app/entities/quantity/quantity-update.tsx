import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IQtyUnit } from 'app/shared/model/qty-unit.model';
import { getEntities as getQtyUnits } from 'app/entities/qty-unit/qty-unit.reducer';
import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IQuantity } from 'app/shared/model/quantity.model';
import { getEntity, updateEntity, createEntity, reset } from './quantity.reducer';

export const QuantityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const qtyUnits = useAppSelector(state => state.qtyUnit.entities);
  const items = useAppSelector(state => state.item.entities);
  const quantityEntity = useAppSelector(state => state.quantity.entity);
  const loading = useAppSelector(state => state.quantity.loading);
  const updating = useAppSelector(state => state.quantity.updating);
  const updateSuccess = useAppSelector(state => state.quantity.updateSuccess);

  const handleClose = () => {
    navigate('/quantity');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getQtyUnits({}));
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
    if (values.quantity !== undefined && typeof values.quantity !== 'number') {
      values.quantity = Number(values.quantity);
    }
    if (values.minQuantity !== undefined && typeof values.minQuantity !== 'number') {
      values.minQuantity = Number(values.minQuantity);
    }

    const entity = {
      ...quantityEntity,
      ...values,
      qtyUnit: qtyUnits.find(it => it.id.toString() === values.qtyUnit.toString()),
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
          ...quantityEntity,
          qtyUnit: quantityEntity?.qtyUnit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.quantity.home.createOrEditLabel" data-cy="QuantityCreateUpdateHeading">
            <Translate contentKey="restaurantApp.quantity.home.createOrEditLabel">Create or edit a Quantity</Translate>
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
                  id="quantity-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.quantity.quantity')}
                id="quantity-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.quantity.minQuantity')}
                id="quantity-minQuantity"
                name="minQuantity"
                data-cy="minQuantity"
                type="text"
              />
              <ValidatedField
                id="quantity-qtyUnit"
                name="qtyUnit"
                data-cy="qtyUnit"
                label={translate('restaurantApp.quantity.qtyUnit')}
                type="select"
              >
                <option value="" key="0" />
                {qtyUnits
                  ? qtyUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/quantity" replace color="info">
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

export default QuantityUpdate;
