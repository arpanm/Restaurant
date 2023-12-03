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
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { getEntities as getMealPlans } from 'app/entities/meal-plan/meal-plan.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { getEntities as getCarts } from 'app/entities/cart/cart.reducer';
import { ICartItem } from 'app/shared/model/cart-item.model';
import { getEntity, updateEntity, createEntity, reset } from './cart-item.reducer';

export const CartItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const items = useAppSelector(state => state.item.entities);
  const mealPlans = useAppSelector(state => state.mealPlan.entities);
  const carts = useAppSelector(state => state.cart.entities);
  const cartItemEntity = useAppSelector(state => state.cartItem.entity);
  const loading = useAppSelector(state => state.cartItem.loading);
  const updating = useAppSelector(state => state.cartItem.updating);
  const updateSuccess = useAppSelector(state => state.cartItem.updateSuccess);

  const handleClose = () => {
    navigate('/cart-item');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getItems({}));
    dispatch(getMealPlans({}));
    dispatch(getCarts({}));
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

    const entity = {
      ...cartItemEntity,
      ...values,
      item: items.find(it => it.id.toString() === values.item.toString()),
      meal: mealPlans.find(it => it.id.toString() === values.meal.toString()),
      cart: carts.find(it => it.id.toString() === values.cart.toString()),
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
          ...cartItemEntity,
          item: cartItemEntity?.item?.id,
          meal: cartItemEntity?.meal?.id,
          cart: cartItemEntity?.cart?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.cartItem.home.createOrEditLabel" data-cy="CartItemCreateUpdateHeading">
            <Translate contentKey="restaurantApp.cartItem.home.createOrEditLabel">Create or edit a CartItem</Translate>
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
                  id="cart-item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.cartItem.quantity')}
                id="cart-item-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField id="cart-item-item" name="item" data-cy="item" label={translate('restaurantApp.cartItem.item')} type="select">
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="cart-item-meal" name="meal" data-cy="meal" label={translate('restaurantApp.cartItem.meal')} type="select">
                <option value="" key="0" />
                {mealPlans
                  ? mealPlans.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="cart-item-cart" name="cart" data-cy="cart" label={translate('restaurantApp.cartItem.cart')} type="select">
                <option value="" key="0" />
                {carts
                  ? carts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cart-item" replace color="info">
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

export default CartItemUpdate;
