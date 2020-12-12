export interface JwtToken {
  /**
   * Expiration time (seconds since Unix epoch)
   */
  exp: number;

  /**
   * Issued at (seconds since Unix epoch)
   */
  iat: number;

  /**
   * Issuer (who created and signed this token)
   */
  iss: string;

  /**
   * Not valid before (seconds since Unix epoch)
   */
  nbf: number;

  /**
   * Roles for the subject
   */
  roles: string[];

  /**
   * Subject (whom the token refers to)
   */
  sub: string;
}
