import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './otp.reducer';

export const OtpDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const otpEntity = useAppSelector(state => state.otp.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="otpDetailsHeading">
          <Translate contentKey="restaurantApp.otp.detail.title">Otp</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{otpEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="restaurantApp.otp.email">Email</Translate>
            </span>
          </dt>
          <dd>{otpEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="restaurantApp.otp.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{otpEntity.phone}</dd>
          <dt>
            <span id="otp">
              <Translate contentKey="restaurantApp.otp.otp">Otp</Translate>
            </span>
          </dt>
          <dd>{otpEntity.otp}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="restaurantApp.otp.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{otpEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="isValidated">
              <Translate contentKey="restaurantApp.otp.isValidated">Is Validated</Translate>
            </span>
          </dt>
          <dd>{otpEntity.isValidated ? 'true' : 'false'}</dd>
          <dt>
            <span id="expiry">
              <Translate contentKey="restaurantApp.otp.expiry">Expiry</Translate>
            </span>
          </dt>
          <dd>{otpEntity.expiry ? <TextFormat value={otpEntity.expiry} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="restaurantApp.otp.usr">Usr</Translate>
          </dt>
          <dd>{otpEntity.usr ? otpEntity.usr.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/otp" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/otp/${otpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OtpDetail;
