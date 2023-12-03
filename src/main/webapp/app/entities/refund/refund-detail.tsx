import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './refund.reducer';

export const RefundDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const refundEntity = useAppSelector(state => state.refund.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="refundDetailsHeading">
          <Translate contentKey="restaurantApp.refund.detail.title">Refund</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{refundEntity.id}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="restaurantApp.refund.status">Status</Translate>
            </span>
          </dt>
          <dd>{refundEntity.status}</dd>
          <dt>
            <span id="vendor">
              <Translate contentKey="restaurantApp.refund.vendor">Vendor</Translate>
            </span>
          </dt>
          <dd>{refundEntity.vendor}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="restaurantApp.refund.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{refundEntity.amount}</dd>
          <dt>
            <span id="initDate">
              <Translate contentKey="restaurantApp.refund.initDate">Init Date</Translate>
            </span>
          </dt>
          <dd>{refundEntity.initDate ? <TextFormat value={refundEntity.initDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="restaurantApp.refund.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>{refundEntity.updatedDate ? <TextFormat value={refundEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="restaurantApp.refund.payment">Payment</Translate>
          </dt>
          <dd>{refundEntity.payment ? refundEntity.payment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/refund" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/refund/${refundEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RefundDetail;
