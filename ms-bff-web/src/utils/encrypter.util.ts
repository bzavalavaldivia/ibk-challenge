import {
  CipherCCM,
  DecipherCCM,
  createCipheriv,
  createDecipheriv,
  randomBytes,
  scrypt,
} from 'crypto';
import { promisify } from 'util';

export class Encrypter {
  public static async encrypt(value: string): Promise<string> {
    const iv = randomBytes(16);
    const salt = randomBytes(32);
    const key = (await promisify(scrypt)(
      'ENCRYPTER_SECRET',
      salt,
      32,
    )) as Buffer;
    const cipher: CipherCCM = createCipheriv('aes-256-gcm', key, iv);
    const encrypted = Buffer.concat([
      cipher.update(value, 'utf8'),
      cipher.final(),
    ]);
    const tag = cipher.getAuthTag();
    return `${iv.toString('hex')}$${salt.toString('hex')}$${tag.toString(
      'hex',
    )}$${encrypted.toString('hex')}`;
  }

  public static async decrypt(value: string): Promise<string> {
    const [iv, salt, tag, encrypted] = value
      .split('$')
      .map((x) => Buffer.from(x, 'hex'));
    const key = (await promisify(scrypt)(
      'ENCRYPTER_SECRET',
      salt,
      32,
    )) as Buffer;
    const decipher: DecipherCCM = createDecipheriv('aes-256-gcm', key, iv);
    decipher.setAuthTag(tag);
    const decrypted = Buffer.concat([
      decipher.update(encrypted),
      decipher.final(),
    ]);
    return decrypted.toString();
  }
}
