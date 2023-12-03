import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order.reducer';

export const OrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderEntity = useAppSelector(state => state.order.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderDetailsHeading">
          <Translate contentKey="restaurantApp.order.detail.title">Order</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderEntity.id}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="restaurantApp.order.status">Status</Translate>
            </span>
          </dt>
          <dd>{orderEntity.status}</dd>
          <dt>
            <span id="orderDate">
              <Translate contentKey="restaurantApp.order.orderDate">Order Date</Translate>
            </span>
          </dt>
          <dd>{orderEntity.orderDate ? <TextFormat value={orderEntity.orderDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="restaurantApp.order.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{orderEntity.amount}</dd>
          <dt>
            <span id="couponValue">
              <Translate contentKey="restaurantApp.order.couponValue">Coupon Value</Translate>
            </span>
          </dt>
          <dd>{orderEntity.couponValue}</dd>
          <dt>
            <Translate contentKey="restaurantApp.order.billingLoc">Billing Loc</Translate>
          </dt>
          <dd>{orderEntity.billingLoc ? orderEntity.billingLoc.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.order.coupon">Coupon</Translate>
          </dt>
          <dd>{orderEntity.coupon ? orderEntity.coupon.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order/${orderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderDetail;
