import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PincodeMaster from './pincode-master';
import Location from './location';
import ApplicationUser from './application-user';
import Otp from './otp';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="pincode-master/*" element={<PincodeMaster />} />
        <Route path="location/*" element={<Location />} />
        <Route path="application-user/*" element={<ApplicationUser />} />
        <Route path="otp/*" element={<Otp />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
