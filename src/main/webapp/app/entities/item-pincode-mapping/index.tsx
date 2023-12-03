import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ItemPincodeMapping from './item-pincode-mapping';
import ItemPincodeMappingDetail from './item-pincode-mapping-detail';
import ItemPincodeMappingUpdate from './item-pincode-mapping-update';
import ItemPincodeMappingDeleteDialog from './item-pincode-mapping-delete-dialog';

const ItemPincodeMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ItemPincodeMapping />} />
    <Route path="new" element={<ItemPincodeMappingUpdate />} />
    <Route path=":id">
      <Route index element={<ItemPincodeMappingDetail />} />
      <Route path="edit" element={<ItemPincodeMappingUpdate />} />
      <Route path="delete" element={<ItemPincodeMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ItemPincodeMappingRoutes;
