import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './weight-info.reducer';

export const WeightInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const weightInfoEntity = useAppSelector(state => state.weightInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="weightInfoDetailsHeading">
          <Translate contentKey="restaurantApp.weightInfo.detail.title">WeightInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{weightInfoEntity.id}</dd>
          <dt>
            <span id="currentWeight">
              <Translate contentKey="restaurantApp.weightInfo.currentWeight">Current Weight</Translate>
            </span>
          </dt>
          <dd>{weightInfoEntity.currentWeight}</dd>
          <dt>
            <span id="expectedWeight">
              <Translate contentKey="restaurantApp.weightInfo.expectedWeight">Expected Weight</Translate>
            </span>
          </dt>
          <dd>{weightInfoEntity.expectedWeight}</dd>
          <dt>
            <span id="heightInInch">
              <Translate contentKey="restaurantApp.weightInfo.heightInInch">Height In Inch</Translate>
            </span>
          </dt>
          <dd>{weightInfoEntity.heightInInch}</dd>
          <dt>
            <span id="numberOfDays">
              <Translate contentKey="restaurantApp.weightInfo.numberOfDays">Number Of Days</Translate>
            </span>
          </dt>
          <dd>{weightInfoEntity.numberOfDays}</dd>
        </dl>
        <Button tag={Link} to="/weight-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/weight-info/${weightInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WeightInfoDetail;
