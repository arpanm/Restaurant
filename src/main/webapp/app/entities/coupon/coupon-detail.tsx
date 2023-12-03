import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './coupon.reducer';

export const CouponDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const couponEntity = useAppSelector(state => state.coupon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="couponDetailsHeading">
          <Translate contentKey="restaurantApp.coupon.detail.title">Coupon</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{couponEntity.id}</dd>
          <dt>
            <span id="maxValue">
              <Translate contentKey="restaurantApp.coupon.maxValue">Max Value</Translate>
            </span>
          </dt>
          <dd>{couponEntity.maxValue}</dd>
          <dt>
            <span id="maxPercentage">
              <Translate contentKey="restaurantApp.coupon.maxPercentage">Max Percentage</Translate>
            </span>
          </dt>
          <dd>{couponEntity.maxPercentage}</dd>
          <dt>
            <span id="minOrderValue">
              <Translate contentKey="restaurantApp.coupon.minOrderValue">Min Order Value</Translate>
            </span>
          </dt>
          <dd>{couponEntity.minOrderValue}</dd>
        </dl>
        <Button tag={Link} to="/coupon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/coupon/${couponEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CouponDetail;
