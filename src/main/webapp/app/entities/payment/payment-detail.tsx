import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payment.reducer';

export const PaymentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const paymentEntity = useAppSelector(state => state.payment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentDetailsHeading">
          <Translate contentKey="restaurantApp.payment.detail.title">Payment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.id}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="restaurantApp.payment.status">Status</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.status}</dd>
          <dt>
            <span id="vendor">
              <Translate contentKey="restaurantApp.payment.vendor">Vendor</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.vendor}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="restaurantApp.payment.type">Type</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.type}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="restaurantApp.payment.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.amount}</dd>
          <dt>
            <span id="initDate">
              <Translate contentKey="restaurantApp.payment.initDate">Init Date</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.initDate ? <TextFormat value={paymentEntity.initDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="restaurantApp.payment.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {paymentEntity.updatedDate ? <TextFormat value={paymentEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="totalRefundAmount">
              <Translate contentKey="restaurantApp.payment.totalRefundAmount">Total Refund Amount</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.totalRefundAmount}</dd>
          <dt>
            <Translate contentKey="restaurantApp.payment.order">Order</Translate>
          </dt>
          <dd>{paymentEntity.order ? paymentEntity.order.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentDetail;
