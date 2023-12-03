import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './item-pincode-mapping.reducer';

export const ItemPincodeMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itemPincodeMappingEntity = useAppSelector(state => state.itemPincodeMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemPincodeMappingDetailsHeading">
          <Translate contentKey="restaurantApp.itemPincodeMapping.detail.title">ItemPincodeMapping</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemPincodeMappingEntity.id}</dd>
          <dt>
            <span id="serviceability">
              <Translate contentKey="restaurantApp.itemPincodeMapping.serviceability">Serviceability</Translate>
            </span>
          </dt>
          <dd>{itemPincodeMappingEntity.serviceability}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="restaurantApp.itemPincodeMapping.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{itemPincodeMappingEntity.pincode}</dd>
          <dt>
            <Translate contentKey="restaurantApp.itemPincodeMapping.item">Item</Translate>
          </dt>
          <dd>{itemPincodeMappingEntity.item ? itemPincodeMappingEntity.item.id : ''}</dd>
          <dt>
            <Translate contentKey="restaurantApp.itemPincodeMapping.restaurant">Restaurant</Translate>
          </dt>
          <dd>{itemPincodeMappingEntity.restaurant ? itemPincodeMappingEntity.restaurant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/item-pincode-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item-pincode-mapping/${itemPincodeMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemPincodeMappingDetail;
