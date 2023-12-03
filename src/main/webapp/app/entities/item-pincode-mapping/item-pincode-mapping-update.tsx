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
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { getEntities as getRestaurants } from 'app/entities/restaurant/restaurant.reducer';
import { IItemPincodeMapping } from 'app/shared/model/item-pincode-mapping.model';
import { getEntity, updateEntity, createEntity, reset } from './item-pincode-mapping.reducer';

export const ItemPincodeMappingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const items = useAppSelector(state => state.item.entities);
  const restaurants = useAppSelector(state => state.restaurant.entities);
  const itemPincodeMappingEntity = useAppSelector(state => state.itemPincodeMapping.entity);
  const loading = useAppSelector(state => state.itemPincodeMapping.loading);
  const updating = useAppSelector(state => state.itemPincodeMapping.updating);
  const updateSuccess = useAppSelector(state => state.itemPincodeMapping.updateSuccess);

  const handleClose = () => {
    navigate('/item-pincode-mapping');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getItems({}));
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

    const entity = {
      ...itemPincodeMappingEntity,
      ...values,
      item: items.find(it => it.id.toString() === values.item.toString()),
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
      ? {}
      : {
          ...itemPincodeMappingEntity,
          item: itemPincodeMappingEntity?.item?.id,
          restaurant: itemPincodeMappingEntity?.restaurant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.itemPincodeMapping.home.createOrEditLabel" data-cy="ItemPincodeMappingCreateUpdateHeading">
            <Translate contentKey="restaurantApp.itemPincodeMapping.home.createOrEditLabel">Create or edit a ItemPincodeMapping</Translate>
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
                  id="item-pincode-mapping-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.itemPincodeMapping.serviceability')}
                id="item-pincode-mapping-serviceability"
                name="serviceability"
                data-cy="serviceability"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.itemPincodeMapping.pincode')}
                id="item-pincode-mapping-pincode"
                name="pincode"
                data-cy="pincode"
                type="text"
              />
              <ValidatedField
                id="item-pincode-mapping-item"
                name="item"
                data-cy="item"
                label={translate('restaurantApp.itemPincodeMapping.item')}
                type="select"
              >
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="item-pincode-mapping-restaurant"
                name="restaurant"
                data-cy="restaurant"
                label={translate('restaurantApp.itemPincodeMapping.restaurant')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item-pincode-mapping" replace color="info">
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

export default ItemPincodeMappingUpdate;
