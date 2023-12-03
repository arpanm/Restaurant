import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './application-user.reducer';

export const ApplicationUserDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const applicationUserEntity = useAppSelector(state => state.applicationUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicationUserDetailsHeading">
          <Translate contentKey="restaurantApp.applicationUser.detail.title">ApplicationUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="restaurantApp.applicationUser.name">Name</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.name}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="restaurantApp.applicationUser.password">Password</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.password}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="restaurantApp.applicationUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="restaurantApp.applicationUser.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.phone}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="restaurantApp.applicationUser.role">Role</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.role}</dd>
          <dt>
            <span id="isPhoneValidated">
              <Translate contentKey="restaurantApp.applicationUser.isPhoneValidated">Is Phone Validated</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.isPhoneValidated ? 'true' : 'false'}</dd>
          <dt>
            <span id="isEmailValidated">
              <Translate contentKey="restaurantApp.applicationUser.isEmailValidated">Is Email Validated</Translate>
            </span>
          </dt>
          <dd>{applicationUserEntity.isEmailValidated ? 'true' : 'false'}</dd>
          <dt>
            <span id="phoneValidatedOn">
              <Translate contentKey="restaurantApp.applicationUser.phoneValidatedOn">Phone Validated On</Translate>
            </span>
          </dt>
          <dd>
            {applicationUserEntity.phoneValidatedOn ? (
              <TextFormat value={applicationUserEntity.phoneValidatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="emailValidatedOn">
              <Translate contentKey="restaurantApp.applicationUser.emailValidatedOn">Email Validated On</Translate>
            </span>
          </dt>
          <dd>
            {applicationUserEntity.emailValidatedOn ? (
              <TextFormat value={applicationUserEntity.emailValidatedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="restaurantApp.applicationUser.restaurant">Restaurant</Translate>
          </dt>
          <dd>{applicationUserEntity.restaurant ? applicationUserEntity.restaurant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/application-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/application-user/${applicationUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicationUserDetail;
