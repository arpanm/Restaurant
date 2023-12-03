import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './qty-unit.reducer';

export const QtyUnitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const qtyUnitEntity = useAppSelector(state => state.qtyUnit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="qtyUnitDetailsHeading">
          <Translate contentKey="restaurantApp.qtyUnit.detail.title">QtyUnit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{qtyUnitEntity.id}</dd>
          <dt>
            <span id="unitName">
              <Translate contentKey="restaurantApp.qtyUnit.unitName">Unit Name</Translate>
            </span>
          </dt>
          <dd>{qtyUnitEntity.unitName}</dd>
        </dl>
        <Button tag={Link} to="/qty-unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/qty-unit/${qtyUnitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QtyUnitDetail;
