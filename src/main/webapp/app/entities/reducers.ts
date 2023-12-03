import pincodeMaster from 'app/entities/pincode-master/pincode-master.reducer';
import location from 'app/entities/location/location.reducer';
import applicationUser from 'app/entities/application-user/application-user.reducer';
import otp from 'app/entities/otp/otp.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  pincodeMaster,
  location,
  applicationUser,
  otp,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
