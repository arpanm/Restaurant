import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './item.reducer';

export const ItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itemEntity = useAppSelector(state => state.item.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemDetailsHeading">
          <Translate contentKey="restaurantApp.item.detail.title">Item</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemEntity.id}</dd>
          <dt>
            <span id="itemName">
              <Translate contentKey="restaurantApp.item.itemName">Item Name</Translate>
            </span>
          </dt>
          <dd>{itemEntity.itemName}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="restaurantApp.item.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{itemEntity.imageUrl}</dd>
          <dt>
            <span id="properties">
              <Translate contentKey="restaurantApp.item.properties">Properties</Translate>
            </span>
          </dt>
          <dd>{itemEntity.properties}</dd>
          <dt>
            <span id="foodType">
              <Translate contentKey="restaurantApp.item.foodType">Food Type</Translate>
            </span>
          </dt>
          <dd>{itemEntity.foodType}</dd>
          <dt>
            <Translate contentKey="restaurantApp.item.nutrition">Nutrition</Translate>
          </dt>
          <dd>{itemEntity.nutrition ? itemEntity.nutrition.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.item.price">Price</Translate>
          </dt>
          <dd>{itemEntity.price ? itemEntity.price.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.item.quantity">Quantity</Translate>
          </dt>
          <dd>{itemEntity.quantity ? itemEntity.quantity.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.item.ingredience">Ingredience</Translate>
          </dt>
          <dd>
            {itemEntity.ingrediences
              ? itemEntity.ingrediences.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {itemEntity.ingrediences && i === itemEntity.ingrediences.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="restaurantApp.item.category">Category</Translate>
          </dt>
          <dd>{itemEntity.category ? itemEntity.category.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item/${itemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemDetail;
