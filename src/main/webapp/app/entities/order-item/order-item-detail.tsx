import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-item.reducer';

export const OrderItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderItemDetailsHeading">
          <Translate contentKey="restaurantApp.orderItem.detail.title">OrderItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.id}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="restaurantApp.orderItem.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.quantity}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="restaurantApp.orderItem.status">Status</Translate>
            </span>
          </dt>
          <dd>{orderItemEntity.status}</dd>
          <dt>
            <Translate contentKey="restaurantApp.orderItem.item">Item</Translate>
          </dt>
          <dd>{orderItemEntity.item ? orderItemEntity.item.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.orderItem.meal">Meal</Translate>
          </dt>
          <dd>{orderItemEntity.meal ? orderItemEntity.meal.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.orderItem.deliveryLoc">Delivery Loc</Translate>
          </dt>
          <dd>{orderItemEntity.deliveryLoc ? orderItemEntity.deliveryLoc.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.orderItem.restaurant">Restaurant</Translate>
          </dt>
          <dd>{orderItemEntity.restaurant ? orderItemEntity.restaurant.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.orderItem.orderAssignedTo">Order Assigned To</Translate>
          </dt>
          <dd>{orderItemEntity.orderAssignedTo ? orderItemEntity.orderAssignedTo.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.orderItem.order">Order</Translate>
          </dt>
          <dd>{orderItemEntity.order ? orderItemEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-item/${orderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderItemDetail;
