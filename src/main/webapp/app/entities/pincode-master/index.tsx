import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PincodeMaster from './pincode-master';
import PincodeMasterDetail from './pincode-master-detail';
import PincodeMasterUpdate from './pincode-master-update';
import PincodeMasterDeleteDialog from './pincode-master-delete-dialog';

const PincodeMasterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PincodeMaster />} />
    <Route path="new" element={<PincodeMasterUpdate />} />
    <Route path=":id">
      <Route index element={<PincodeMasterDetail />} />
      <Route path="edit" element={<PincodeMasterUpdate />} />
      <Route path="delete" element={<PincodeMasterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PincodeMasterRoutes;
