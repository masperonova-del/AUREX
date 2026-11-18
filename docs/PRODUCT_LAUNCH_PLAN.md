# NOVA PAY — Product and launch plan

Status: working document for the 1.2 prototype. This is not a launch authorization or a claim that financial services are live.

## 1. Product direction

Build a premium, privacy-conscious mobile experience for people who want a simple way to explore digital wallet features and, only through properly authorized payment partners, access real payment services.

Initial problem hypothesis for Gabon: users often encounter friction when trying to pay for digital services and online purchases. Validate this with interviews before committing to payment rails, pricing or country expansion.

## 2. Product modes

### Guest preview

- No phone number and no email required.
- Local, randomly generated guest ID.
- Fixed session choices: 1 hour, 24 hours, 7 days or 30 days.
- Visible expiration date and time.
- No real money, deposits, withdrawals, transfers, card credentials or payment execution.
- At expiry or guest logout, clear the guest's local demo data.

### Local demo account

- Optional phone field.
- Email and password are local prototype credentials only.
- No remote synchronization until a real backend authentication flow is implemented and tested.

### Future regulated account

This is a separate product mode, not an extension of guest access. Do not use a guest ID or a temporary session to bypass identity, age, fraud or compliance checks imposed by law or a regulated provider.

The user may choose not to provide a phone number only where the payment partner's approved onboarding rules permit another verification method. Any genuine balance must remain protected and redeemable according to the account terms; expiry must never erase real funds. A temporary virtual card or payment token is a separate object with its own issuer-controlled expiry and revocation rules.

## 3. Experience and visual system

- Deep ink background: #070A10.
- Midnight blue surfaces: #111724 and #18263D.
- Champagne gold accent: #D9BD79.
- Silver primary text and muted blue-grey secondary text.
- Large editorial headings, carefully spaced cards, restrained borders, consistent corner radii and clear focus states.
- One main action per screen; transaction status and fees must be explicit.
- Keep accessibility in view: contrast, scalable text, screen-reader labels, touch targets and reduced-motion behavior.

## 4. Delivery phases

### Phase A — Prototype completion

- Finish guest expiry, account screens, dashboard, cards, activity and profile.
- Clearly label all balances and transactions as fictitious.
- Add UI, session-expiry, data-isolation and regression tests.
- Produce a debug APK only after CI passes; do not distribute a debug build as a public financial app.

### Phase B — Closed usability test

- Invite a small group of adult testers with clear consent.
- Test on multiple Android versions and low-cost devices.
- Collect only product feedback and technical diagnostics needed for the test.
- Do not collect real card details, bank credentials or real money.

### Phase C — Partner and compliance discovery

- Identify the exact services required: payment initiation, merchant payments, mobile money, virtual cards or stored value.
- Discuss the model with an authorized payment institution, bank or issuer that can legally support the selected service in the target countries.
- Agree on roles, customer support, safeguarding of funds, settlement, reversals, refunds, disputes, fees, fraud monitoring and service availability.
- Obtain qualified legal and compliance review before any public service involving funds or payments.

### Phase D — Controlled pilot

- Only after written partner approval, legal review, security testing and operational readiness.
- Start with one narrowly scoped service and one market.
- Use transaction limits and manual monitoring defined with the regulated partner.
- Publish accurate service availability, fees, eligibility, terms and privacy information.

### Phase E — Expansion

- Expand to other CEMAC markets only after validating demand, provider coverage, customer support and local requirements in each market.
- Add more payment rails only when the partner confirms support and the integration passes end-to-end tests.

## 5. Launch measures

Prototype and usability:
- install-to-first-screen success;
- guest-flow completion;
- session-expiry correctness;
- crash-free sessions;
- accessibility and device compatibility;
- user comprehension of demo-versus-real money.

Future authorized pilot:
- successful transaction rate by provider;
- pending, failed, reversed and settled outcomes;
- support response and resolution time;
- fraud and dispute indicators;
- transparent total cost per transaction;
- customer retention and repeat usage.

Do not use growth targets as a substitute for safety, regulatory readiness or product reliability.

## 6. Release gates

A release involving real money or payment execution is blocked until all are true:

1. The applicable authorization path and permitted operating model are confirmed by qualified counsel and the relevant authorities.
2. The regulated partner has approved the product flow and contractual roles.
3. Authentication, account recovery, authorization, audit logs and server-side controls have been reviewed.
4. Security testing and independent review of critical flows are complete.
5. Data protection notices, retention schedules, user rights and any required formalities are reviewed.
6. Customer support, incident response, refunds, complaints and reconciliation are operational.

Until then, NOVA PAY remains a demonstration app with fictitious balances and transactions.
