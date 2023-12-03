import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './quantity.reducer';

export const QuantityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const quantityEntity = useAppSelector(state => state.quantity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="quantityDetailsHeading">
          <Translate contentKey="restaurantApp.quantity.detail.title">Quantity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{quantityEntity.id}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="restaurantApp.quantity.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{quantityEntity.quantity}</dd>
          <dt>
            <span id="minQuantity">
              <Translate contentKey="restaurantApp.quantity.minQuantity">Min Quantity</Translate>
            </span>
          </dt>
          <dd>{quantityEntity.minQuantity}</dd>
          <dt>
            <Translate contentKey="restaurantApp.quantity.qtyUnit">Qty Unit</Translate>
          </dt>
          <dd>{quantityEntity.qtyUnit ? quantityEntity.qtyUnit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/quantity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/quantity/${quantityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuantityDetail;
