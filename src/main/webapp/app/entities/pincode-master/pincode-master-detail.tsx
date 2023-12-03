import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pincode-master.reducer';

export const PincodeMasterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pincodeMasterEntity = useAppSelector(state => state.pincodeMaster.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pincodeMasterDetailsHeading">
          <Translate contentKey="restaurantApp.pincodeMaster.detail.title">PincodeMaster</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pincodeMasterEntity.id}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="restaurantApp.pincodeMaster.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{pincodeMasterEntity.pincode}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="restaurantApp.pincodeMaster.area">Area</Translate>
            </span>
          </dt>
          <dd>{pincodeMasterEntity.area}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="restaurantApp.pincodeMaster.city">City</Translate>
            </span>
          </dt>
          <dd>{pincodeMasterEntity.city}</dd>
          <dt>
            <span id="stateProvince">
              <Translate contentKey="restaurantApp.pincodeMaster.stateProvince">State Province</Translate>
            </span>
          </dt>
          <dd>{pincodeMasterEntity.stateProvince}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="restaurantApp.pincodeMaster.country">Country</Translate>
            </span>
          </dt>
          <dd>{pincodeMasterEntity.country}</dd>
        </dl>
        <Button tag={Link} to="/pincode-master" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pincode-master/${pincodeMasterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PincodeMasterDetail;
