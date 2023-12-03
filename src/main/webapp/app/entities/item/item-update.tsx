import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INutrition } from 'app/shared/model/nutrition.model';
import { getEntities as getNutritions } from 'app/entities/nutrition/nutrition.reducer';
import { IPrice } from 'app/shared/model/price.model';
import { getEntities as getPrices } from 'app/entities/price/price.reducer';
import { IQuantity } from 'app/shared/model/quantity.model';
import { getEntities as getQuantities } from 'app/entities/quantity/quantity.reducer';
import { IIngredienceMaster } from 'app/shared/model/ingredience-master.model';
import { getEntities as getIngredienceMasters } from 'app/entities/ingredience-master/ingredience-master.reducer';
import { IFoodCategory } from 'app/shared/model/food-category.model';
import { getEntities as getFoodCategories } from 'app/entities/food-category/food-category.reducer';
import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { getEntities as getMealPlanItems } from 'app/entities/meal-plan-item/meal-plan-item.reducer';
import { IItem } from 'app/shared/model/item.model';
import { FoodType } from 'app/shared/model/enumerations/food-type.model';
import { getEntity, updateEntity, createEntity, reset } from './item.reducer';

export const ItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nutritions = useAppSelector(state => state.nutrition.entities);
  const prices = useAppSelector(state => state.price.entities);
  const quantities = useAppSelector(state => state.quantity.entities);
  const ingredienceMasters = useAppSelector(state => state.ingredienceMaster.entities);
  const foodCategories = useAppSelector(state => state.foodCategory.entities);
  const mealPlanItems = useAppSelector(state => state.mealPlanItem.entities);
  const itemEntity = useAppSelector(state => state.item.entity);
  const loading = useAppSelector(state => state.item.loading);
  const updating = useAppSelector(state => state.item.updating);
  const updateSuccess = useAppSelector(state => state.item.updateSuccess);
  const foodTypeValues = Object.keys(FoodType);

  const handleClose = () => {
    navigate('/item');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getNutritions({}));
    dispatch(getPrices({}));
    dispatch(getQuantities({}));
    dispatch(getIngredienceMasters({}));
    dispatch(getFoodCategories({}));
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
    if (values.properties !== undefined && typeof values.properties !== 'number') {
      values.properties = Number(values.properties);
    }

    const entity = {
      ...itemEntity,
      ...values,
      ingrediences: mapIdList(values.ingrediences),
      nutrition: nutritions.find(it => it.id.toString() === values.nutrition.toString()),
      price: prices.find(it => it.id.toString() === values.price.toString()),
      quantity: quantities.find(it => it.id.toString() === values.quantity.toString()),
      category: foodCategories.find(it => it.id.toString() === values.category.toString()),
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
          foodType: 'Veg',
          ...itemEntity,
          nutrition: itemEntity?.nutrition?.id,
          price: itemEntity?.price?.id,
          quantity: itemEntity?.quantity?.id,
          ingrediences: itemEntity?.ingrediences?.map(e => e.id.toString()),
          category: itemEntity?.category?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="restaurantApp.item.home.createOrEditLabel" data-cy="ItemCreateUpdateHeading">
            <Translate contentKey="restaurantApp.item.home.createOrEditLabel">Create or edit a Item</Translate>
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
                  id="item-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('restaurantApp.item.itemName')}
                id="item-itemName"
                name="itemName"
                data-cy="itemName"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.item.imageUrl')}
                id="item-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.item.properties')}
                id="item-properties"
                name="properties"
                data-cy="properties"
                type="text"
              />
              <ValidatedField
                label={translate('restaurantApp.item.foodType')}
                id="item-foodType"
                name="foodType"
                data-cy="foodType"
                type="select"
              >
                {foodTypeValues.map(foodType => (
                  <option value={foodType} key={foodType}>
                    {translate('restaurantApp.FoodType.' + foodType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="item-nutrition"
                name="nutrition"
                data-cy="nutrition"
                label={translate('restaurantApp.item.nutrition')}
                type="select"
              >
                <option value="" key="0" />
                {nutritions
                  ? nutritions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="item-price" name="price" data-cy="price" label={translate('restaurantApp.item.price')} type="select">
                <option value="" key="0" />
                {prices
                  ? prices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="item-quantity"
                name="quantity"
                data-cy="quantity"
                label={translate('restaurantApp.item.quantity')}
                type="select"
              >
                <option value="" key="0" />
                {quantities
                  ? quantities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('restaurantApp.item.ingredience')}
                id="item-ingredience"
                data-cy="ingredience"
                type="select"
                multiple
                name="ingrediences"
              >
                <option value="" key="0" />
                {ingredienceMasters
                  ? ingredienceMasters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="item-category"
                name="category"
                data-cy="category"
                label={translate('restaurantApp.item.category')}
                type="select"
              >
                <option value="" key="0" />
                {foodCategories
                  ? foodCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/item" replace color="info">
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

export default ItemUpdate;
